alias jettyk="kill -9 `ps -ef | grep jetty:run | grep -v grep| awk '{print $2}'`"
alias ls='ls -la'
alias run='mvn jetty:run'
alias clean='mvn clean'

export MAVEN_OPTS="-Xms512M -Xmx2048M -XX:MaxPermSize=2048M -XX:+UseConcMarkSweepGC -noverify"
#-javaagent:/home/sofoklis/ZeroTurnaround/JRebel/jrebel.jar -Drebel.lift_plugin=true"

export JAVA_OPTS="-Xms256m -Xmx512m -XX:MaxPermSize=256m $JAVA_OPTS"

export PATH="$PATH:~/devbin"
###"-javaagent:/home/sofoklis/ZeroTurnaround/JRebel/jrebel.jar -Xms256m -Xmx512m -XX:MaxPermSize=256m $JAVA_OPTS"

export PROJECTFOLDER="/home/sofoklis/workspace/starter"
export RECREATEDB="no"
     
