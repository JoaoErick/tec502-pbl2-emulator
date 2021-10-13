# tec502-pbl2-emulator

<p align="center">
  <img src="https://i.imgur.com/pjlmn0P.png" alt="emulator icon" width="300px" height="300px">
</p>

------------

## 📚 Descrição ##
**Resolução do problema 2 do MI - Concorrência e Conectividade (TEC 502).**<br/><br/>
O projeto tem como função emular um dispositivo de monitoramento de COVID-19, que se comunica com uma [*Fog*](https://github.com/AllanCapistrano/tec502-pbl2-fog) através do procolo *MQTT*. Ele envia de tempos em tempos os valores dos sensores, que são gerados de forma **aleatória** com base na **têndencia** (normal ou grave; também gerado de forma aleatória) do paciente fictício, para a [*Fog*](https://github.com/AllanCapistrano/tec502-pbl2-fog).<br/><br/>
**Esse dispositivo possui diversos sensores, tais como:**

- Sensor de temperatura corporal;
- Sensor de frequência respiratória;
- Sensor de nível de oxigenação do sangue;
- Sensor de pressão arterial;
- Sensor de frequência cardíaca.

Antes de começar a enviar os valores dos sensores, o mesmo realiza um processo de *handshake* com a [*Fog*](https://github.com/AllanCapistrano/tec502-pbl2-fog) para saber em qual tópico *MQTT* o mesmo irá publicar. Isso é feito para garantir que o emulador envie os dados para a *thread* certa da [*Fog*](https://github.com/AllanCapistrano/tec502-pbl2-fog) certa.

### ⛵ Navegação pelos projetos: ###
- [Servidor](https://github.com/AllanCapistrano/tec502-pbl2-server)
- [Fog](https://github.com/AllanCapistrano/tec502-pbl2-fog)
- \>Emulador de Sensores
- [Monitoramento de Pacientes](https://github.com/JoaoErick/tec502-pbl2-monitoring)

### 🔗 Tecnologias utilizadas: ### 
- [Java JDK 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html)

### 📊 Dependências: ### 
- [JSON](https://www.json.org/json-en.html)
- [Eclipse Paho 1.2.5](https://www.eclipse.org/paho/index.php?page=clients/java/index.php)
- [Java Faker 1.0.2](https://github.com/DiUS/java-faker)
- [Apache Commons Lang 3.11](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.11)
- [SnakeYAML 1.23](https://mvnrepository.com/artifact/org.yaml/snakeyaml/1.23)

------------

## 🖥️ Como utilizar ##
Para o utilizar este projeto é necessário ter instalado o JDK 8u111.

- [JDK 8u111 com Netbeans 8.2](https://www.oracle.com/technetwork/java/javase/downloads/jdk-netbeans-jsp-3413139-esa.html)
- [JDK 8u111](https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html)

### Através de uma IDE ###
1. Primeiramente verifique se todas as depedências¹ estão adicionadas no projeto;
2. Caso não estejam, não é necessário fazer o *download*, as mesmas estão disponível em `src` > [`libs`](https://github.com/JoaoErick/tec502-pbl2-emulator/tree/main/src/libs) , sendo necessário somente adicioná-las de acordo com a sua IDE;
3. Após isso, basta **executar o projeto**, por exemplo, utilizando o *NetBeans IDE 8.2* aperte o botão `F6`.

###### Obs¹: A dependência JSON, não está em src > libs, pois a mesma não está disponível em `.jar`, porém, já está adicionada no pacote [org.json](https://github.com/JoaoErick/tec502-pbl2-emulator/tree/main/src/org/json), não sendo necessário fazer o *download* e/ou adicioná-la no projeto. ######

------------

## 📌 Autores ##
- Allan Capistrano: [Github](https://github.com/AllanCapistrano) - [Linkedin](https://www.linkedin.com/in/allancapistrano/) - [E-mail](https://mail.google.com/mail/u/0/?view=cm&fs=1&tf=1&source=mailto&to=asantos@ecomp.uefs.br)
- João Erick Barbosa: [Github](https://github.com/JoaoErick) - [Linkedin](https://www.linkedin.com/in/joão-erick-barbosa-9050801b0/) - [E-mail](https://mail.google.com/mail/u/0/?view=cm&fs=1&tf=1&source=mailto&to=jsilva@ecomp.uefs.br)

------------

## ⚖️ Licença ##
[MIT License (MIT)](./LICENSE)
