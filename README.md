# solr-whitelist-filter

Ce filtre ne conserve que des tokens déclarés dans un fichier.
Il est développé sur la base du StopFilter, mais plutot que de rejeter tous les tokens présents dans le fichier, il rejette tous les tokens qui ne sont pas dans le fichier.

Testé avec Solr 8.11.2


## Construire le jar avec la commande (répertoire build)

mvn package

Placer le fichier jar dans le répertoire commun à tous les index (probablement /var/solr/data/lib)

## Utiliser le fitre dans un type

```
<fieldtype name="auteur-whitelist" class="solr.TextField">
    <analyzer type="index">
        <tokenizer class="solr.StandardTokenizerFactory"/>
        <filter class="solr.ASCIIFoldingFilterFactory"/>
        <filter class="solr.ElisionFilterFactory" ignoreCase="true" articles="contractions_fr.txt"/>
        <filter class="solr.LowerCaseFilterFactory"/>
        <filter class="org.apache.lucene.analysis.core.WhiteListFilterFactory" ignoreCase="true" words="whitelist_auteurs.txt" />
    </analyzer>
    <analyzer type="query">
        <tokenizer class="solr.StandardTokenizerFactory"/>
        <filter class="solr.ASCIIFoldingFilterFactory"/>
        <filter class="solr.ElisionFilterFactory" ignoreCase="true" articles="contractions_fr.txt"/>
        <filter class="solr.LowerCaseFilterFactory"/>
    </analyzer>
</fieldtype>
```

