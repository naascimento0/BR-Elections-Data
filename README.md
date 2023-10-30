# BR-Elections-Data

## Sobre

Trabalho com o intuito de processar informações sobre a eleição legislativa do ano de 2022 para Deputados Federais e Deputados Estaduais.

## Como executar
1. Compile
```sh
ant compile
```
2. Compacte os arquivos .class em deputados.jar
```sh
ant jar
```
3. Execute o arquivo deputados.jar
```sh
java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>
```
