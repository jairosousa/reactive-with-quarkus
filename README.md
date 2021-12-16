# reactive-with-quarkus Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/reactive-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): Reactive implementation of JAX-RS with additional features. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- GETTING STARTED WITH REACTIVE ([guide](https://quarkus.io/guides/getting-started-reactive))

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

# API Fruts

O código é um pouco mais complicado. Para gravar em um banco de dados, precisamos de uma transação. Portanto, usamos Panache.withTransactionpara obter um (de forma assíncrona) e chamar o persistmétodo quando recebemos a transação. O persistmétodo também está retornando um Uni. Isso Uniemite o resultado da inserção da fruta no banco de dados. Assim que a inserção for concluída (e essa é a nossa continuação), criamos uma 201 CREATEDresposta. RESTEasy Reactive lê automaticamente o corpo da solicitação como JSON e cria a Fruitinstância.

> **_NOTE:_** O `.onItem().transform(…​)` pode ser substituído por `.map(…​).map` é um atalho.


## Teste e execução
Se você tiver curl em sua máquina, pode tentar o endpoint usando:
```shell script
> curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"peach"}' \
  http://localhost:8080/fruits
```

Testar um aplicativo reativo é semelhante a testar um não reativo: use o ponto de extremidade HTTP e verifique as respostas HTTP. O fato de o aplicativo ser reativo não muda nada.

Em FruitsEndpointTest.java, você pode ver como o teste para o aplicativo de frutas pode ser implementado.

O empacotamento e a execução do aplicativo também não mudam. O ./mvnw packagee ./mvnw package -Pnativepara gerar um executável nativo funcionam normalmente. Você também pode empacotar o aplicativo em um contêiner.

Para executar o aplicativo, não se esqueça de iniciar um banco de dados e fornecer a configuração para o seu aplicativo.

Por exemplo, você pode usar o Docker para executar seu banco de dados:

```shell script
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 \
    --name postgres-quarkus -e POSTGRES_USER=quarkus \
    -e POSTGRES_PASSWORD=quarkus -e POSTGRES_DB=fruits \
    -p 5432:5432 postgres:13.1
```

Em seguida, inicie o aplicativo usando:

```shell script
java \
   -Dquarkus.datasource.reactive.url=postgresql://localhost/fruits \
   -Dquarkus.datasource.username=quarkus \
   -Dquarkus.datasource.password=quarkus \
   -jar target/quarkus-app/quarkus-run.jar
```

Ou, se você empacotou seu aplicativo como executável nativo, use:

```shell script
./target/getting-started-with-reactive-runner \
  -Dquarkus.datasource.reactive.url=postgresql://localhost/fruits \
  -Dquarkus.datasource.username=quarkus \
  -Dquarkus.datasource.password=quarkus
```

Os parâmetros passados para o aplicativo são descritos no guia da fonte de dados. Existem outras maneiras de configurar o aplicativo - verifique o guia de configuração para ter uma visão geral das possibilidades (como variável env, arquivos .env e assim por diante).

## Indo além
Este guia é uma breve introdução a alguns recursos reativos oferecidos pelo Quarkus. O Quarkus é uma estrutura reativa e, portanto, oferece muitos recursos reativos.

Se você deseja continuar neste tópico, verifique:

* [A arquitetura reativa Quarkus](https://quarkus.io/guides/quarkus-reactive-architecture)

* [Mutiny - uma biblioteca de programação reativa intuitiva](https://quarkus.io/guides/mutiny-primer)