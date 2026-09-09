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
    GREATER_THAN_OR_EQUAL,
    IN_INTERVAL,
    NULL_OR_LESS_THAN,
    NULL_OR_LESS_THAN_OR_EQUAL,
    NULL_OR_GREATER_THAN,
    NULL_OR_GREATER_THAN_OR_EQUAL,
    NULL_OR_IN_INTERVAL
}
