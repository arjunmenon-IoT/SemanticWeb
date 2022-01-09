function exec {
  if [[ -d $1 ]] ; then 
    for dir in $( ls $1 ) ; do
      exec $1/$dir;
    done
    return
  fi
  [[ ! $1 = *.ttl ]] && return
  echo $1;

  filename=${1##*/}
  basename=${filename%.ttl}

  [[ $basename = index ]] && return

  # add prefix seas: if not available 
  grep -q "seas:" $1 || {
    echo "add @prefix seas:"
    # find last occurrence of @prefix
    line=$(grep -n "@prefix" $1 | cut -d: -f1 | tail -n 1) 
    sed -i "$line a @prefix seas: <https://w3id.org/seas/> ." $1
  }

  echo -e "\n\n<$basename> seas:temperature <$basename#temperature> .
<$basename#temperature> a seas:TemperatureProperty .

<$basename> seas:humidity <$basename#humidity> .
<$basename#humidity> a seas:HumidityProperty .

<$basename> seas:luminosity <$basename#luminosity> .
<$basename#luminosity> a seas:LuminosityProperty .

<$basename> seas:atmosphericPressure <$basename#atmosphericPressure> .
<$basename#atmosphericPressure> a seas:AtmosphericPressureProperty ." >> $1
}


exec $1


#exec ./1ET/100.ttl