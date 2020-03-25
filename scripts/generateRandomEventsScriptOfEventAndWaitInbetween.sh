#!/usr/bin/env bash

sleepTime=$2
operation=$1

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
nameNumber=0
files=()
num=0
arrOps=(copyFileSameDir moveFile copyFileDiffDir editFile moveFileToTrash renameFile create)

#arrTargetPaths=("/Users/Agnes/Dropbox/test" "/Users/Agnes/Desktop/test" "/Users/Agnes/Desktop/test-2" "/Volumes/USB" "/Users/Agnes/Desktop/sample" "/Users/Agnes/Google Drive/test" "/Users/Agnes/Desktop/sample")
arrTargetPaths=("/Users/Agnes/Dropbox/test" "/Users/Agnes/Desktop/test" "/Users/Agnes/Desktop/test-2" "/Volumes/USB" "/Users/Agnes/Desktop/sample" "/Users/Agnes/Desktop/sample")
#arrTargetPaths=("/Users/Agnes/Desktop/test" "/Users/Agnes/Desktop/test-2" "/Users/Agnes/Desktop/sample")
arrFileExt=(xml txt xlsx docx xml)
arrWaitTime=(60 30 15 7,5 3,75 1,88 0,94)

editFile(){
    fileToEdit=$(findFile)
    addLogEntry "Created_Modified;""$fileToEdit"";"
    echo open "'""$fileToEdit""'" >> $eventScript
    open "$fileToEdit"
    echo 'sleep 10' >> $eventScript
    sleep 10
    #send some text to open file and hit cmd+s to save changes and close program again
    echo 'cliclick w:1000 kd:cmd t:v ku:cmd' >> $eventScript
    cliclick w:1000 kd:cmd t:v ku:cmd
    echo 'cliclick w:500 kd:cmd t:s ku:cmd w:1000 kd:cmd t:q' >> $eventScript
    cliclick w:500 kd:cmd t:s ku:cmd w:1000 kd:cmd t:q
}

findTargetDirToCopyFile(){
    fileToCopy=$1
    # get random target directory to copy to
    calcRandom $((${#arrTargetPaths[@]}-1))
    targetDir="${arrTargetPaths[$num]}"
    dir=$(dirname "$fileToCopy")
    basename=$(basename -- "$fileToCopy")
    newDirAndName=$targetDir"/"$basename
    diff=false

    #check if current file and "new file" (to copy to) are identical
    cmp --silent "$fileToCopy" "$newDirAndName" || diff=true
    if [ "$diff" = true ] ;
    then
       echo $targetDir
    else
       findTargetDirToCopyFile "$fileToCopy"
    fi
}

copyFileDiffDir(){
    fileToCopy=$(findFile)
    targetDir=$(findTargetDirToCopyFile "$fileToCopy")

    addLogEntry "Created_Copied;""$fileToCopy"";""$targetDir"
    echo cp "'""$fileToCopy""'" "'""$targetDir""'" >> $eventScript
    cp "$fileToCopy" "$targetDir"
}

copyFileSameDir(){
    fileToCopy=$(findFile)

    dir=$(dirname "$fileToCopy")
    filename=$(basename -- "$fileToCopy")
    extension="${filename##*.}"
    filename="${filename%.*}"

    #create new filename + previous path
    newDirAndFilename=$dir"/"$filename" copy."$extension

    addLogEntry "Created_Copied;""$fileToCopy"";""$newDirAndFilename"
    echo cp "'""$fileToCopy""'" "'""$newDirAndFilename""'" >> $eventScript
    cp "$fileToCopy" "$newDirAndFilename"
}

findFile(){
    # get random selection of files to move
    getFilesOfDirectory
    # select random file from files selection
    calcRandom $((${#files[@]}-1))
    file="${files[$num]}"

    if [ -e "$file" ]
    then
        echo $file
    else
        findFile
    fi
}

findTargetDirectoryToMoveFileTo(){
    fileToMove=$1
    # get random target directory to copy to
    calcRandom $((${#arrTargetPaths[@]}-1))
    targetDir="${arrTargetPaths[$num]}"
    originalDir=$(dirname "$fileToMove")
    if [ "$originalDir" != "$targetDir" ] ;
    then
        echo $targetDir
    else
        findTargetDirectoryToMoveFileTo "$fileToMove"
    fi
}

moveFile(){
    fileToMove=$(findFile)
    targetDir=$(findTargetDirectoryToMoveFileTo "$fileToMove")
    addLogEntry "Moved;""$fileToMove"";""$targetDir"
    echo mv "'""$fileToMove""'" "'""$targetDir""'" >> $eventScript
    mv "$fileToMove" "$targetDir"
}

moveFileToTrash(){
    fileToMove=$(findFile)
    addLogEntry "MovedToRecycleBin;""$fileToMove"";/Users/Agnes/.Trash"
    echo mv "'""$fileToMove""'" "/Users/Agnes/.Trash/" >> $eventScript
    mv "$fileToMove" "/Users/Agnes/.Trash/"
}

renameFile(){
    fileToRename=$(findFile)
    dir=$(dirname "$fileToRename")
    filename=$(basename "$fileToRename")
    extension="${filename##*.}"
    filename="${filename%.*}"
    newName=$dir"/"$filename"X."$extension

    addLogEntry "Renamed;""$fileToRename"";""$newName"
    echo mv "'""$fileToRename""'" "'""$newName""'" >> $eventScript
    mv "$fileToRename" "$newName"
}

create(){
    # get random target directory to create a file in
    calcRandom $((${#arrTargetPaths[@]}-1))
    targetDir="${arrTargetPaths[$num]}"

    # get random file extension
    arrFileExt2=(xml txt xlsx docx)
    calcRandom $((${#arrFileExt2[@]}-1))
    fileExt="${arrFileExt2[$num]}"

    incrementNameNumber
    echo increment name number, current nameNumber: $nameNumber

    filename="test""$nameNumber"".""$fileExt"
    filepath="$targetDir""/""$filename"

    #if [[ "$fileExt" == "xlsx" ]]; then
     #   echo 'open -a "Microsoft Excel"' >> $eventScript
      #  open -a "Microsoft Excel"
       # echo 'sleep 2' >> $eventScript
        #sleep 2
        #echo 'cliclick kd:cmd t:n ku:cmd' >> $eventScript
    #    cliclick kd:cmd t:n ku:cmd
     #   echo 'cliclick kd:cmd t:v ku:cmd' >> $eventScript
      #  cliclick kd:cmd t:v ku:cmd
       # echo 'sleep 3' >> $eventScript
        #sleep 3
#        echo 'cliclick kd:cmd t:s ku:cmd' >> $eventScript
 #       cliclick kd:cmd t:s ku:cmd
  #      timestamp=$(date +%s)
   #     filename="test"$nameNumber
    #    pathname="$targetDir""/"$filename
     #   addLogEntry "create;""$pathname"".xlsx;"
      #  echo 'cliclick w:500 t:'$pathname >> $eventScript
       # cliclick w:500 t:"$pathname"
        #echo 'sleep 3' >> $eventScript
#        sleep 3
 #       echo 'cliclick kp:enter' >> $eventScript
  #      cliclick kp:enter
   #     echo 'sleep 3' >> $eventScript
    #    sleep 3
     #   echo 'cliclick kp:enter' >> $eventScript
      #  cliclick kp:enter
       # echo 'sleep 1' >> $eventScript
        #sleep 1
     #   echo 'cliclick kd:cmd t:q '>> $eventScript
    #    cliclick kd:cmd t:q
  #  else
        addLogEntry "Created;""$filepath"";"
        echo touch "'""$filepath""'" >> $eventScript
        touch "$filepath"
    #fi
}

# returns a random number between 0 and a max (parameter)
calcRandom(){
    if [ $1 == 0 ]; then
        num=0
    else
        num=$(( ( RANDOM % $1 ) + 0 ))
    fi
}

# saves all files of a directory (1. input parameter) in array "files"
getFilesOfDirectory(){
    calcRandom $((${#arrTargetPaths[@]}-1))
    if [ $num == -1 ]; then
        echo no event triggered
        exit 1
    else
        path=${arrTargetPaths[$num]}
        for f in "$path"/*; do
            if [[ -f "$f" ]]; then
               files+=("$f")
            fi
        done
    fi
    if [ ${#files[@]} == 0 ]; then
       #echo no files found in dir ${arrTargetPaths[$num]} - no event triggered
       getFilesOfDirectory
    fi
}

function incrementNameNumber {
    nameNumber=$(($nameNumber + 1))
}

addLogEntry(){
    echo "echo" "'"$1";""'"'$(date +%Y-%m-%d-%H:%M:%S)'"'"";;;""'" ">> $tmpfile"  >> $eventScript
    echo "$1"";"$(date +%Y-%m-%d-%H:%M:%S)";;;" >> $tmpfile
}

transformOperation(){
    $1
    sleep $sleepTime
    echo "sleep" "'"$sleepTime"'" >> $eventScript
    transformOperation $1
}

timestamp="$(date +%Y-%m-%d.%H-%M-%S)"
mkdir randomlogevents
cd randomlogevents
tmpfile=${DIR}/randomlogevents/logRandomEvents"."${timestamp}.txt
eventScript=${DIR}/randomlogevents/randomEventsGenerated"."${timestamp}.sh
touch $tmpfile

case $1 in

      move)
        transformOperation "moveFile"
        ;;

      create)
        transformOperation "create"
        ;;

      edit)
        transformOperation "editFile"
        ;;

      rename)
        transformOperation "renameFile"
        ;;

      copySameDir)
        transformOperation "copyFileSameDir"
        ;;

      copyDiffDir)
        transformOperation "copyFileDiffDir"
        ;;

      delete)
        transformOperation "moveFileToTrash"
        ;;

      *)
        echo "unknown operation"
        ;;
    esac


    echo "echo" "----------------------------------END-ITERATION----------------------------------" ">> $tmpfile"  >> $eventScript
    echo "----------------------------------END-ITERATION----------------------------------" >> $tmpfile