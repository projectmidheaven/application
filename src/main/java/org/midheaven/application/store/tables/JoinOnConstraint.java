package org.midheaven.application.store.tables;

public record JoinOnConstraint(QualifiedColumn left , QualifiedColumn right, ValueMatchOperator operator) {


}
