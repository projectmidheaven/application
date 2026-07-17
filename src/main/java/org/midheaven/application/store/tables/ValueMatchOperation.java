package org.midheaven.application.store.tables;

public enum ValueMatchOperation {
    EQUALITY,
    NULL,
    CONTAINS_TEXT,
    ENDS_WITH_TEXT,
    STARTS_WITH_TEXT,
    IN,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL
}
