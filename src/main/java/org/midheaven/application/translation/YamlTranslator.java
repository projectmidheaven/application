package org.midheaven.application.translation;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Strings;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class YamlTranslator extends AbstractTranslator {
    
    private final Map<String, Object> tree;
    
    public YamlTranslator(@NotNullable String text) {
        Yaml yml = new Yaml();
        tree = yml.load(text);
    }
    
    @Override
    protected Maybe<String> resolveText(@NotNullable String key) {
        var splitter = Strings.Splitter.split(key).by('.');
        
        Map<String, Object> currentNode = tree;
        while(!splitter.isEmpty()){
            var name = splitter.first().orNull();
            var object = currentNode.get(name);
            if (object == null){
                return Maybe.none();
            } else if (object instanceof String message){
                if ( splitter.count().toInt() == 1){
                    // match
                    return Maybe.of(message);
                } else {
                    /// path does not exist
                    return Maybe.none();
                }
            }  else {
                currentNode = (Map<String, Object>) object;
            }
            splitter = splitter.withoutFirst();
        }
        return Maybe.none();
    }
}
