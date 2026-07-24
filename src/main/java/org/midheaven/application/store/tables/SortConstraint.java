package org.midheaven.application.store.tables;

public record SortConstraint(QualifiedColumn column, SortOrder sortOrder) implements ColumnConstraint {

}
