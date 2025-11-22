# FiadoPay Refatorado (H2)

Refatoração do Gateway de pagamento **FiadoPay** para a AVII/POOA.
Substitui PSPs reais com um backend em memória (H2).

## Como Rodar

- Executar o Projeto (Dar Run)

- Abrir o swagger UI


- Abrir o H2

```bash
usuario=sa
senha=
url=jdbc:h2:mem:fiadopay
```

H2 console: http://localhost:8080/h2  
Swagger UI: http://localhost:8080/swagger-ui.html

## Sugentão para testes

JSON de Requisição (AuthRequestDTO)
```bash
{
  "username": "usuario@exemplo.com",
  "password": "senha123"
}
```
> Resposta Esperada (200 OK)
-------
PaymentController (Criação de Pagamento)

Este endpoint cria um novo pagamento e possui uma verificação de idempotência e a anotação customizada @AuthCheck.

POST /payment

-------
JSON de Requisição (CreatePaymentDTO)
```bash
{
  "amount": 550.75,
  "currency": "BRL",
  "payerId": "8f0a2d3e-4c5b-6a7d-8e9f-0a1b2c3d4e5f",
  "recipientId": "a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6",
  "description": "Compra de materiais de escritório."
}
```
-------
WebhookController (Recebimento de Eventos)

Este endpoint recebe eventos assíncronos de sistemas externos.

POST /webhook
-------
JSON da Requisição (payload)
```bash
{
  "eventType": "PAYMENT_SETTLED",
  "timestamp": "2025-11-22T15:00:00Z",
  "data": {
    "transactionId": "tx-abc-12345",
    "status": "COMPLETED",
    "value": 550.75
  }
}
```

Respostas Esperadas
- Sucesso (200 OK): Se o header X-Signature for batata.

- Falha de Autorização (401 Unauthorized): Se o header X-Signature estiver faltando ou não for batata.
-------
## Relatorio

Foi realizada a implementação de Mappers (PaymentMapper, AuthMapper, WebhookMapper) para conversão segura de dados.

PaymentProcessorImpl implementa a interface PaymentProcessor usando ExecutorService com pool de 5 threads. Recebe um Payment e submete para processamento assíncrono via PaymentWorker.

PaymentWorker implementa Runnable e executa o processamento de cada pagamento em thread separada. Atualiza status para PROCESSING, simula processamento de 2 segundos, e define status final como APPROVED (80% dos casos) ou DECLINED (20% dos casos). Em caso de exceção, marca como FAILED. Persiste as mudanças de status no banco via PaymentRepository.

WebhookDispatcherImpl implementa a interface WebhookDispatcher usando ExecutorService com pool de 3 threads. Recebe um WebhookEvent e submete para processamento assíncrono via WebhookWorker.

WebhookWorker implementa Runnable e executa o processamento de cada webhook em thread separada. Valida a assinatura, simula processamento de 1.5 segundos, e atualiza status para VALIDATED com timestamp de processamento. Se assinatura for inválida ou ocorrer exceção, marca como REJECTED. Persiste via WebhookEventRepository.

Os três repositories (PaymentRepository, WebhookEventRepository, AuthTokenRepository) são interfaces que estendem JpaRepository do Spring Data JPA, permitindo operações de persistência no H2.

Foi também utilizada três estratégias de registro distintas, cada uma explorando um recurso diferente do Spring Framework:

`AntiFraudRuleRegistry`: utiliza ApplicationContext para encontrar todos os Beans anotados com `@AntiFraud`
`PaymentMethodRegistry`: utiliza ApplicationContext para encontrar todos os Beans anotados com `@PaymentMethod`
`WebhookSinkRegistry`: identifica e armazena os métodos anotados com `@WebhookSink`

Alguns Endpoints também foram implementados(Controllers)

 POST /payment: Recebe o pedido de pagamento, converte DTO, aplica regras e envia para processamento. Protegido pela anotação @AuthCheck.

 POST /auth: Endpoint (mock) que gera tokens AuthToken válidos para teste.

 POST /webhook: Recebe notificações externas e valida a assinatura de segurança (X-Signature).

Criação do Payment: O coração do sistema. Já gerencia seus próprios timestamps (createdAt, updatedAt), gera seu UUID e controla seu Status (PENDING, APPROVED, etc).

Criação do WebhookEvent: Modelo preparado para filas de processamento, com suporte a validação de assinatura e status de envio.

Criação do AuthToken: Modelo de segurança com lógica interna de expiração (isExpired()).


### Sistema de contratos e plugins 

AuthCheckRule: Interface para criar regras de validação.

@AuthCheck: Anotação para marcar métodos que precisam de validação dinâmica (via reflexão).

PaymentProcessor e WebhookDispatcher: Interfaces que definem como o sistema processa dados, permitindo que a lógica seja implementada depois sem quebrar o código.

O Payment já tem UUID e Enums prontos para serem salvos.

O WebhookEvent já tem os campos de payload e assinatura que o H2 precisa.

### Arquitetura de Dados

Foi feita a criação de DTOs (Request/Response) para desacoplar a API do Domínio.

## Regras de Negócio (Service)

- Cálculo de Juros: Implementado no PaymentService. Aplica juros simples de 1% ao mês para pagamentos parcelados via Cartão.

- Idempotência: O Controller aceita e repassa o header Idempotency-Key para o domínio.

## Annotations

`@AntiFraud`: marca beans que atuam como regras na análise de fraudes.                                     
`@PaymentMethod`: para associar um PaymentProcessor a um PaymentMethodType específico (ex: CARD, PIX), permitindo a seleção dinâmica do processador.           
`@WebhookSink: marca métodos específicos dentro de um bean que devem ser invocados para processar eventos de webhook`.
