package com.dmdev.entity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum Language {
    @Enumerated(EnumType.STRING)
    JAVA, C, RUBY
}
