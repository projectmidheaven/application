package org.midheaven.application.store.tables;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.Strings;

public class EditableColumnMetadata implements ColumnMetadata{
    
    private final ColumnType type;
    private final String name;
    private final String physicalName;
    private boolean required;
    private boolean primary;
    private boolean isUnique;
    private Integer maxLength;
    private Integer minLength;
    private boolean isNeverAbsentAfterSet;
    private String referesTo;
    
    public EditableColumnMetadata(String name, ColumnType type){
        this.name = name;
        this.type = type;
        this.physicalName = TableCasing.toTableCasing(name);
    }
    
    public EditableColumnMetadata(ColumnMetadata other) {
        this.name = other.logicName();
        this.physicalName = other.physicalName();
        this.type = other.type();
        this.required = other.isRequired();
        this.primary = other.isPrimary();
        this.isUnique = other.isUnique();
        this.maxLength = other.maxLength().orNull();
        this.minLength = other.minLength().orNull();
        this.isNeverAbsentAfterSet = other.isNeverAbsentAfterSet();
        this.referesTo = other.referesTo().orNull();
    }
    
    @Override
    public String logicName() {
        return name;
    }
    
    @Override
    public String physicalName() {
        return physicalName;
    }
    
    @Override
    public ColumnType type() {
        return type;
    }
    
    @Override
    public boolean isRequired() {
        return required;
    }
    
    @Override
    public boolean isPrimary() {
        return primary;
    }
    
    @Override
    public boolean isUnique() {
        return isUnique;
    }
    
    @Override
    public boolean isNeverAbsentAfterSet() {
        return isNeverAbsentAfterSet;
    }
    
    @Override
    public Maybe<String> referesTo() {
        return Strings.filled(referesTo);
    }
    
    public void setReferesTo(String referesTo){
        this.referesTo = referesTo;
    }
    
    @Override
    public Maybe<Integer> maxLength() {
        return Maybe.of(maxLength);
    }
    
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
    
    @Override
    public Maybe<Integer> minLength() {
        return Maybe.of(minLength);
    }
    
    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }
    
    public EditableColumnMetadata isPrimaryKey(boolean primary) {
        this.primary = primary;
        return this;
    }
    
    public EditableColumnMetadata withUnique(boolean unique) {
        this.isUnique = unique;
        return this;
    }
    
    public EditableColumnMetadata withRequired(boolean required) {
        this.required = required;
        return this;
    }
    
    public EditableColumnMetadata isNeverAbsentAfterSet(boolean isNeverAbsentAfterSet) {
        this.isNeverAbsentAfterSet = isNeverAbsentAfterSet;
        return this;
    }
}
