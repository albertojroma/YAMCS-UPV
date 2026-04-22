# Software necesario

Para trabajar cómodamente en este repositorio se recomienda tener instalado:

## Visual Studio Code 

Es un editor de código que incluye la posibilidad de usar el terminal del sistema. Se recomienda instalar las siguientes extensiones para el proyecto
 
  * Better Comments
  * XML Language Support by Red Hat
  * La *suite* de extensiones de Python creadas por Microsoft

## git

Es una herramienta que permite el control de versiones por líneas de comandos (o usando la interfaz de *vscode*). Para clonar el proyecto se recomienda tener instalado **git** en la máquina donde se va a trabajar

## conda

Es un gestor de contenedores para python. Para instalarlo se deben seguir estos pasos:

* Nos aseguramos de tener instalada uns versión de python igual o superior a la 3.8:
  * **Linux**: ```python --version```

* Se instala el cliente de Yamcs de *PyPi*:
Es altamente **recomendable** instalar el entorno de python de Yamcs en un contenedor aislado (usando ```conda``` por ejemplo). 
Para ello se deben seguir los siguientes pasos:

  1. Actualiza e instala wget (por si acaso)
  
      ```sudo apt update && sudo apt install wget -y```
  
  2. Descargar e instalar ***miniconda***

      ***Conda*** es un gestor de entornos virtuales y librerías. Miniconda es la versión mínima del instalador de ***miniconda***, la cual incluye únicamente Python, los paquetes de los que ambos dependen, y un reducido número de otros paquetes útiles.

      2.1. Descargar instalador de miniconda 
      
      ``wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh``
      
      2.2. Permitir los permisos de ejecución del instalador
      
      ``chmod +x Miniconda3-latest-Linux-x86_64.sh``
      
      2.3. Ejecutar instalardor 
      
      ``./Miniconda3-latest-Linux-x86_64.sh``
    
      Durante la instalación, se recomienda contestar yes a todas las preguntas yes/no. Por otro lado, se debe cerrar y abrir el terminal o ejecutar `source ~/.bashrc` para que se apliquen los cambios correctamente.
      
      Nota: se recomienda ejecutar ``conda config --set auto_activate_base false`` al ejecutar ***miniconda*** por primera vez. De esta manera, no se activa cada vez que abrimos un terminal.

  3. Configuración del entorno
      
      3.1. Crear entorno virtual
      
      `conda create --name yamcs python=3.11`

      Se debe aceptar todo lo que salga durante la creación del entorno

      3.2 Entrar al entorno

      `conda activate yamcs`

  4. Instalar cliente de python de Yamcs
    
      `pip install yamcs-client`
