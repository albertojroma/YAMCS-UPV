# *Training*

Esta rama contiene el contenido del [*training*](https://gitlab.com/acubesat/ops/yamcs-training) creado por [**AcubeSAT**](https://gitlab.com/acubesat) para el software **YAMCS**.

El propósito es familiazizarse con el software **YAMCS** mediante una lista de tareas e instrucciones. Las tareas mencionadas se deben realizar en el orden establecido.

## A tener en cuenta

Es recomendable tener a mano la [guía del desarrollador](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/0.-Introduction-and-Contents) de YAMCS.

En este caso se va a trabajar principalmente con el lenguaje **XML** (principalmente para definir que significan los datos), por lo tanto, es recomendable seguir las indicaciones de instalación de software del repositorio del [*training* de AcubeSAT](https://gitlab.com/acubesat/ops/yamcs-training#overview-of-the-training)

## Guía del *training*

Consta de [11 pasos](https://gitlab.com/acubesat/ops/yamcs-training/-/wikis/New-member-YAMCS-Training-Tasks):

1. [Crear tipos de datos enumerados](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/1)
2. [Crear una estructura utilizando *AggregateParameterType*](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/2) 
3. [(opcional) Crear un parámetro con codificación de tiempo personalizada](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/3)
4. [Crear un contenedor para el encabezado TM principal](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/4)
5. [Crear un contenedor TM personalizado](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/5)
6. [Crear un TM para leer estructuras de mantenimiento](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/6)
7. [Crear un TC [3,1]](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/7)
8. [Utilizar un parámetro de matriz en un TM](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/8)
9. [Crear un enlace de datos UDP y TCP sencillo](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/9)
10. [(opcional) Crear un servicio sencillo](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/10)
11. [Cambiar el protocolo IP de UDP a TCP para el simulador](https://gitlab.com/acubesat/ops/yamcs-training/-/issues/11)

### Paso 1:

Crea dos tipos de datos enumerados en el archivo ```dt.xml```.

1. Crea una [*Enumerated Parameter Type*](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/1.-Parameters-and-Arguments#the-enumerated-parameter-type) que se utilizará como el *Data Type* del parámetro «*Packet Type*» del [*Packet Primary Header*](https://ccsds.org/Pubs/133x0b2e2.pdf#page=32).

**Nota**: El documento del estándar **CCSDS 133.0-B-2** (de donde sale el «*Packet Type*» del *Packet Primary Header*) no es el del repositorio original. En el repositorio original se hacia referencia a **CCSDS 133.0-B-1**, actualmente desfasado.

```
<xtce:EnumeratedParameterType name="PacketType">
    <xtce:IntegerDataEncoding sizeInBits="1"></xtce:IntegerDataEncoding>
    <xtce:EnumerationList>
        <xtce:Enumeration value="0" label="TM" />
        <xtce:Enumeration value="1" label="TC" />
    </xtce:EnumerationList>
</xtce:EnumeratedParameterType>
```

2. Crea una [*Enumerated Parameter Type*](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/1) que se utilizará como el *Data Type* del parámetro «*Application Process Identifier*» (APID) del [*Packet Primary Header*](https://ccsds.org/Pubs/133x0b2e2.pdf#page=32). Tenga en cuenta que, en este caso, las aplicaciones se refieren a los diferentes subsistemas del proyecto AcubeSAT (ordenador de a bordo, comunicaciones, ADCS, Science Union y estación terrestre) y que **puede elegir los valores que desee**.

```
<xtce:EnumeratedParameterType name="APID">
    <xtce:IntegerDataEncoding sizeInBits="11"></xtce:IntegerDataEncoding>
    <xtce:EnumerationList>
        <xtce:Enumeration value="1" label="OBC" />
        <xtce:Enumeration value="2" label="COMMS" />
        <xtce:Enumeration value="3" label="ADCS" />
        <xtce:Enumeration value="4" label="SU" />
        <xtce:Enumeration value="5" label="GS" />
        <!-- en IDLE tienen que estar todos los bits a 1-->
        <xtce:Enumeration value="2047" label="IdlePacket" />
    </xtce:EnumerationList>
</xtce:EnumeratedParameterType>
```

**Nota**: Si no encuentra el tamaño en bits de los parámetros solicitados, consulte también la norma ECSS, ya que es posible que se mencione allí.

