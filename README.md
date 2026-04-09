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

### Paso 2:

Un parámetro agregado (*Aggregate*) es algo parecido a una estructura de C. Contiene una estructura de parámetros. Los contenedores (*Containers*) ofrecen una funcionalidad similar, aunque se utilizan para construir la secuencia de parámetros TC y TM. Se trata de una capa de abstracción superior. Consulte las secciones [4.3.2.4.11](https://public.ccsds.org/Pubs/660x1g2.pdf#page=151) y [5.4](https://public.ccsds.org/Pubs/660x1g2.pdf#page=151) de la descripción de elementos de XTCE. Consulte también la página wiki sobre el [tipo agregado](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/1.-Parameters-and-Arguments#the-aggregate-parameter-type).
Esta tarea consiste en crear una estructura «*AggregateParameterType*» que contenga los siguientes miembros:

| Nombre del parámetro | Tipo |
|--------------  |--------------|
| parameter_ID | parameter_ID |
| samples | uint16_t |
| max_value | float | 
| max_time | uint32_t |
| min_value | float | 
| min_time | uint32_t | 
| standard_deviation | float |


donde ```parameter_ID``` ya está definido en ```dt.xml```

```
<xtce:AggregateParameterType name="sample_structure">
    <xtce:MemberList>
        <xtce:Member typeRef="parameter_ID" name="parameter_ID"></xtce:Member>
        <xtce:Member typeRef="uint16_t" name="samples"></xtce:Member>
        <xtce:Member typeRef="float_t" name="max_value"></xtce:Member>
        <xtce:Member typeRef="uint32_t" name="max_time"></xtce:Member>
        <xtce:Member typeRef="float_t" name="min_value"></xtce:Member>
        <xtce:Member typeRef="uint32_t" name="min_time"></xtce:Member>
        <xtce:Member typeRef="float_t" name="standard_deviation"></xtce:Member>
    </xtce:MemberList>
</xtce:AggregateParameterType>
```

### Paso 3:

El ordenador del satélite no dispone de un reloj integrado. Mide el tiempo utilizando los relojes internos de la unidad microcontroladora (MCU) y lo hace mediante un parámetro de entero sin signo de 32 bits. Este reloj realiza un recuento cada 0,1 s y definimos como 0 la fecha 1/1/2022 00:00:00:000 (hh:mm:ss::ms). Un valor de 1 significaría 1/1/2022 00:00:00:100, un valor de 2 -> 1/1/2022 00:00:00:200, etc. Debe definir un parámetro que convierta esta codificación de tiempo personalizada (CUC) a UNIX. La hora UNIX es el formato de hora utilizado por la mayoría de los ordenadores modernos, sistemas Linux, etc., y el recuento comienza en la Época UNIX, el 1 de enero de 1970 a UTC.

Puede utilizar un [4.3.2.4.9](https://public.ccsds.org/Pubs/660x1g2.pdf#page=146) con un elemento «[*AbsoluteTimeParameterType*](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/1.-Parameters-and-Arguments#the-absolute-time-parameter-type)» o un simple entero con un elemento «[*IntegerDataEncoding*](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/1.-Parameters-and-Arguments#the-integer-data-encoding-element)» utilizando un [4.3.2.2.6.3](https://public.ccsds.org/Pubs/660x1g2.pdf#page=86) *PolynomialCalibrator*.

#### Solución con *IntegerDataEncoding*
```
<xtce:AbsoluteTimeParameterType name="custom_time_encoding_parameter">
    <!-- offset="1640995200" porque offset="0" implica 
        1/1/1970 00:00:00:000 (hh:mm:ss::ms) UTC-->
    <xtce:Encoding scale="10E-1" offset="1640995200">
        <xtce:IntegerDataEncoding sizeInBits="32"></xtce:IntegerDataEncoding>
    </xtce:Encoding>
    <xtce:ReferenceTime>
        <xtce:Epoch>UNIX</xtce:Epoch>
    </xtce:ReferenceTime>
</xtce:AbsoluteTimeParameterType>
```

### Paso 4:
Crea un contenedor [4.3.4](https://public.ccsds.org/Pubs/660x1g2.pdf#page=175) (o la sección 5.4 del [documento](https://public.ccsds.org/Pubs/660x1g2.pdf#page=237) «XTCE Element Description») en el archivo ```pus.xml``` que contenga todos los parámetros del *Primary TM Header* y otro contenedor para el *Secondary TM Header* (consulta la sección [7.4](https://ecss.nl/wp-content/uploads/2016/06/ECSS-E-ST-70-41C15April2016.pdf#page=438) de la norma ECSS y también el archivo [```README.md```](https://gitlab.com/acubesat/ops/yamcs-training/-/blob/main/README.md) para una mejor visualización).

Un *container* es simplemente un grupo de parámetros (u otros contenedores) que se utiliza más de una vez en diferentes comandos o telemetría. Consulte también la página wiki sobre los [*container*](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/2.-Containers).

* Todos los parámetros deben definirse en el mismo archivo (archivo ```pus.xml```).
* Añada nuevos tipos de datos para los parámetros del encabezado primario al archivo ```dt.xml```.


#### *Primary TM Header*
![Primary TM Header](yamcs-training/images/primary_header.png)

##### packet primary header
```
<xtce:SequenceContainer name="PH">
    <xtce:EntryList>
        <xtce:ParameterRefEntry parameterRef="version"></xtce:ParameterRefEntry>
        <xtce:ContainerRefEntry containerRef="packet_id"></xtce:ContainerRefEntry>
        <xtce:ContainerRefEntry containerRef="packet_sequence_control"></xtce:ContainerRefEntry>
        <xtce:ParameterRefEntry parameterRef="packet_data_length"></xtce:ParameterRefEntry>
    </xtce:EntryList>
</xtce:SequenceContainer>
```

##### packet ID container
```
<xtce:SequenceContainer name="packet_id">
    <xtce:EntryList>
        <xtce:ParameterRefEntry parameterRef="packet_type"></xtce:ParameterRefEntry>
        <xtce:ParameterRefEntry parameterRef="secondary_header_flag"></xtce:ParameterRefEntry>
        <xtce:ParameterRefEntry parameterRef="application_process_id"></xtce:ParameterRefEntry>
    </xtce:EntryList>
</xtce:SequenceContainer>
```

##### packet sequence control
```
<xtce:SequenceContainer name="packet_sequence_control">
    <xtce:EntryList>
        <xtce:ParameterRefEntry parameterRef="sequence_flags"></xtce:ParameterRefEntry>
        <xtce:ParameterRefEntry parameterRef="packet_sequence_count"></xtce:ParameterRefEntry>
    </xtce:EntryList>
</xtce:SequenceContainer>
```

#### *Secondary TM Header*
![Secondary TM Header](yamcs-training/images/secondary_header.png)

##### packet secondary header
```
<xtce:SequenceContainer name="SH">
    <xtce:EntryList>
        <xtce:ParameterRefEntry parameterRef="tm_PUS_version"></xtce:ParameterRefEntry>
        <xtce:ParameterRefEntry parameterRef="spacecraft_time_reference_status"></xtce:ParameterRefEntry>
        <xtce:ContainerRefEntry containerRef="message_type_id"></xtce:ContainerRefEntry>
        <xtce:ParameterRefEntry parameterRef="message_type_counter"></xtce:ParameterRefEntry>
        <xtce:ParameterRefEntry parameterRef="destination_id"></xtce:ParameterRefEntry>
        <xtce:ParameterRefEntry parameterRef="time"></xtce:ParameterRefEntry>
    </xtce:EntryList>
</xtce:SequenceContainer>
```

##### message type ID
```
<xtce:SequenceContainer name="message_type_id">
    <xtce:EntryList>
        <xtce:ParameterRefEntry parameterRef="service_type_id"></xtce:ParameterRefEntry>
        <xtce:ParameterRefEntry parameterRef="message_subtype_id"></xtce:ParameterRefEntry>
    </xtce:EntryList>
</xtce:SequenceContainer>
```

