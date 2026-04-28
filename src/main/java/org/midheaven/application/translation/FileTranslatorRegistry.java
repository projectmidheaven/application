package org.midheaven.application.translation;

import org.midheaven.culture.CountryCode;
import org.midheaven.culture.Culture;
import org.midheaven.culture.LanguageCode;
import org.midheaven.io.ByteContent;
import org.midheaven.io.ByteContentFormats;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileTranslatorRegistry implements TranslatorsRegistry{
    
    private final Map<Culture, AbstractTranslator> cultureTranslatorMapping = new ConcurrentHashMap<>();
    private final Map<LanguageCode, AbstractTranslator> languageTranslatorMapping = new ConcurrentHashMap<>();
    
    private final String baseFolderPath;
    private final Culture defaultCulture;
    private final AbstractTranslator parentCultureTranslator;
    
    public FileTranslatorRegistry(String baseFolderPath, Culture defaultCulture){
        this.baseFolderPath = baseFolderPath;
        this.defaultCulture = defaultCulture;
        this.parentCultureTranslator = resolveTranslator(defaultCulture);
    }
    
    @Override
    public Translator translator() {
        return resolveTranslator(defaultCulture);
    }
    
    @Override
    public Translator translator(Culture culture) {
        return resolveTranslator(culture);
    }
    
    private AbstractTranslator resolveTranslator(Culture culture) {
        
        if (culture.countryCode().isAbsent()){
            return readLanguageTranslator(culture.languageCode());
        }
        
        return cultureTranslatorMapping.computeIfAbsent(culture, c -> {
            AbstractTranslator languageTranslator = readLanguageTranslator(culture.languageCode());
            AbstractTranslator countryTranslator = null;
            if (culture.countryCode().isPresent()){
                countryTranslator = readTranslator(culture.languageCode(), culture.countryCode().get());
            }
            
            if (countryTranslator == null){
                return languageTranslator;
            }
            
            return new HierarchicalTranslator(languageTranslator, countryTranslator);
            
        });
        
     
    }
    
    private AbstractTranslator readLanguageTranslator(LanguageCode languageCode) {
        
        return languageTranslatorMapping.computeIfAbsent(languageCode, code -> {
            var path = baseFolderPath + "/" + code.isoCode() + ".yml";
            
            ByteContent content = ByteContent.create().withFormat(ByteContentFormats.YAML).embeddedResource(path);
            
            if (content.isEmpty()){
                return new EmptyTranslator();
            }
            
            return new HierarchicalTranslator(parentCultureTranslator, new YamlTranslator(content.readAsText()));
        });
    }
    
    private AbstractTranslator readTranslator(LanguageCode languageCode, CountryCode countryCode) {
        
        var path = baseFolderPath + "/" + languageCode.isoCode() + "_" + countryCode.isoCode() + ".yml";
        
        ByteContent content = ByteContent.create().withFormat(ByteContentFormats.YAML).embeddedResource(path);
        
        if (content.isEmpty()){
            return new EmptyTranslator();
        }
        
        return new YamlTranslator(content.readAsText());
    }
    
    
}


class HierarchicalTranslator extends AbstractTranslator{
    
    private final AbstractTranslator parent;
    private final AbstractTranslator child;
    
    HierarchicalTranslator (AbstractTranslator parent, AbstractTranslator child){
        this.parent = parent;
        this.child = child;
    }
    
    @Override
    protected Maybe<String> resolveText(@NotNullable String key) {
        return child.resolveText(key).orGet(() -> parent.resolveText(key));
    }
    
}

class EmptyTranslator extends AbstractTranslator{
    
    @Override
    protected Maybe<String> resolveText(@NotNullable String key) {
        return Maybe.none();
    }
}