package com.example.FiadoPayRefatorado.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
   String name();
   double threshold() default 0.0;

}
