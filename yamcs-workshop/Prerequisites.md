# Installation Guide and Prerequisites

Welcome to the yamcs workshop installation guide! This guide contains a list of everything that you will need in order to run yamcs and play the operations game, as well as instructions on how to install everything for [Linux](#1-linux-installation-ubuntu--debian-based), [Windows](#2-windows-installation) and [macOS](#3-macos-installation).

## Prerequisites: 
- Git, which is the tool you will use to locally clone the yamcs-workshop repository from gitlab 
- Java, which is required in order for yamcs to run
- Apache Maven, which is also required in order for yamcs to run
- [VScode](https://code.visualstudio.com/download) or [Intellij Idea](https://www.jetbrains.com/idea/), in order to run yamcs and take a look at the code. The installation process is quite simple for this, just click on the link and download it. If you're on Linux, you can also install it from the Software app. You can find instructions on how to run yamcs on both of these at the end of this file. 

## 1. Linux Installation (Ubuntu & Debian based)


- ### Git 

Git is usually pre-installed on linux systems so you can run the command shown below in your terminal to check if it is already installed for you:
```
git --version
```
If it's not installed for you, then just run: 
```
sudo apt install git
```
After that, verify its installation by running the first command. Then, go ahead and clone the repository by running: 
```
git clone https://gitlab.com/acubesat/ops/yamcs-workshop.git
```


- ### Java

**Disclaimer:** Any jdk11+ version will suffice in order to run yamcs. The instructions presented below are for installing the jdk17 version, but you can follow the exact same process if you wish to install a different version.

Open a terminal and run the command shown below to check if java is already installed for you: 
```
java --version
```
If it isn't, then a list of options will pop up for you. It is recommended to install a LTS jdk version, for example jdk17.  Install it by running the command:  
```
sudo apt -y install openjdk-17-jre-headless
```
After the installation is complete, verify it by running the first command. 


- ### Apache Maven

Open a terminal and run the two commands shown below: 
```
sudo apt update -y
sudo apt install maven -y
```

Verify its installation by running: 
```
mvn --version
```


## 2. Windows Installation


The installation on Windows isn't as simple as on Linux. There are more things that we will need to consider, but if you follow this guide, then everything should work as expected.

- ### Git

To download and install git, visit [this link](https://git-scm.com/download/win) and choose the appropriate git version for you. During the installation process, it is recommended to keep the default installation options.

After you have installed git, you can verify that it's correctly installed by opening the command line and typing what is shown below. You should get the version of git that you are using as a response.
```
git --version
```

Now you can go ahead and clone the yamcs-workshop repository on your local computer by opening the command line and running the two commands shown below. The `cd /` command will change your directory to the root directory, which is usually C:, meaning that the repository will get cloned on C:\yamcs-workshop. Keep this in mind, in order to remember where you can find the repository.
```
cd /
git clone https://gitlab.com/acubesat/ops/yamcs-workshop.git
```

- ### Java

**Disclaimer:** Any jdk11+ version will suffice in order to run yamcs. The instructions presented below are for installing the jdk17 version, but you can follow the exact same process if you wish to install a different version.

To download the jdk-17, which is recommended as it is a LTS version, visit [this link](https://www.oracle.com/java/technologies/downloads/#jdk17-windows) and download the **x64 Installer**. Once you open the installer, make sure to remember the path that jdk-17 will get installed, it's usually **C:\Program Files\Java\jdk-17** but you can change this if you want. Once the installation is finished, open the file explorer and navigate to where you have installed the jdk-17. After that, copy its path, if you have installed it on **C:\Program Files\Java\jdk-17**, then this is what you will need to copy. Then: 

- Go to windows' search bar and search for **environment variables** and click on it.
- On the pop-up window click on **environment variables**.
- Click **new** on the **system variables** and set the name equal to **JAVA_HOME** and then paste the path which you copied beforehand then click **OK**.
- On the **system variables** double click on **Path** and click on **new**. Paste the same thing and add **\bin** at the end. Click on **OK** as many times, in order to close the environment variables.
- Verify the installation by running the command shown belown: 
```
java --version
```
If anything wasn't clear, then you can follow [this video](https://www.youtube.com/watch?v=cL4GcZ6GJV8) for more!

- ### Apache Maven

To download Apache Maven visit [this link](https://maven.apache.org/download.cgi) and download the **apache-maven-3.9.0-bin.zip**. Once the .zip file is downloaded, right click on it, click **show in folder** and then extract it to its own folder, which by default should be **apache-maven-3.9.0-bin**. After that, cut the file you just extracted and paste it on **C:\Program Files**. Click on it and then click again on the next folder inside of it. You should have something like **C:\Program Files\apache-maven-3.9.0-bin\apache-maven-3.9.0**. Copy its path and follow the same exact process you followed to install jdk. The only difference is that the name of the first system variable will be **MAVEN_HOME** instead of **JAVA_HOME**.

To verify its installation, run this command: 
```
mvn --version
```

If anything wasn't clear, then you can follow [this video](https://www.youtube.com/watch?v=km3tLti4TCM) for more!



## 3. macOS Installation

**Disclaimer:** I have never setup a macOS, so there will be no instructions for it. Below you can find some videos and sources that will help you install whatever is necessary: 

- ### Git

    - [Video which explains how to install git](https://www.youtube.com/watch?v=F02LEVYEmQw). Once you have installed git, you can go ahead and clone yamcs by opening a terminal and executing this command: 
    ```
    git clone https://gitlab.com/acubesat/ops/yamcs-workshop.git
    ```

- ### Java

    **Disclaimer:** Any jdk11+ version will suffice in order to run yamcs. The sources presented below are for installing the jdk17 version, but you can follow the exact same process if you wish to install a different version.

    - [Video which explains how to download the jdk-17](https://www.youtube.com/watch?v=SdKIBGnkhDY)
    - [Relevant article for jdk-17](https://www.codejava.net/java-se/install-oracle-jdk-17-on-macos)

- ### Apache Maven

    - [Video which explains how to install maven](https://www.codejava.net/java-se/install-oracle-jdk-17-on-macos). You can follow this guide up to the sixth minute, where you verify that maven is installed by running the `mvn --version` command. There's no need to create a maven project as shown in the video, though feel free to do it if you wish.

## Yamcs on Intellij Idea

The process is the same for Windows, Linux and possibly macOS. You will need to do the following: 

1. open Intellij, File->Open and select the folder which you cloned the repository
2. go to Run -> Edit Configurations -> Add new -> Maven , start writing `yamcs` and select yamcs:run option
3. click on Apply , close the window and that's it

Now you can run Yamcs by simply pressing the run symbol.

![edit_configs](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/uploads/acf0ba7e8bd494c93ca7d02d1b9b5421/edit_configs.png)

![maven_conf](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/uploads/fc2542f54b93c64c7e409d70c6f0d43a/maven_conf.png)


Though unlikely, the XTCE Schema might not be recognised. If that is the case for you then download the .xsd file from [here](https://www.omg.org/spec/XTCE/20180204/SpaceSystem.xsd) by right clicking anywhere on the page and choosing the **save as** option. Then place it to the project root and right click on the `xmlns:xtce` element on top of any .xml file, select import from local source and select the downloaded file.

## Yamcs on VScode 

To open and run yamcs on VScode, open VScode, click on **File** on the top left and then click **Open Folder**. Locate the **yamcs-workshop** folder and open it. Now you will be able to see all the files that are inside the repository. In order to run yamcs, open a new terminal by clicking on **Terminal** and then **New Terminal**. Type the following command on your terminal: 

```
mvn yamcs:run
```

Yamcs will now run on **localhost:8090** and you can navigate to its web interface either by ctrl+click on the link that is in the terminal or by typing **localhost:8090** to your prefered browser.

## Terminating Yamcs if you want to rerun it

Terminating and rerunning yamcs will probably not be needed for the workshop, but if anything occurs, then here is how you can do it.

- ### Intellij Idea: 
    
    If you want to terminate yamcs on Intellij idea, simply click on the **stop** button. You can now rerun it by clicking on the **play** button.

- ### VScode:

    - On Linux the process is quite simple. Just press **ctrl+c** on your VScode terminal. That's it, you can now go ahead and rerun it the same way as before. 
    - On Windows, this doesn't work, as yamcs will still function in the background and if you try to run `mvn yamcs:run`, you will get an error. The way to properly terminate yamcs is either to restart your computer or run the following commands on Windows' command line. After running those commads, you can go ahead and rerun yamcs.

    ```
    netstat -a -o -n | find "8090"
    taskkill /pid <pid_number> /f
    ```

    - Not sure which method will work on macOS, you can go ahead and try the Linux method. If rerunning yamcs produces an error, it means that it's still running, so you'll need to kill it.