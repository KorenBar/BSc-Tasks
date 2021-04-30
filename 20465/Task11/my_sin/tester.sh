#!/bin/bash

printf "[-25.0,25.0]\n\n"

i=-25

while [ $i -le 25 ]
do
  printf "value=$i\n"
  ./my_sin <<< $i
  printf "\n"
  ((i++))
done

