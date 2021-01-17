# EnergyAnalysisAndroid

EnergyAnalysisAndroid is a android energy consumption measurement application.
It measures current and voltage using simple shell script

"""cat /sys/class/power_supply/battery/voltage_now"""
"""cat /sys/class/power_supply/battery/current_now"""

## Build
1) git clone https://github.com/lovgrammer/EnergyAnalysisAndroid.git
2) import project to your Android Studio
3) build

## To utilize App:
1) Click start button
2) Execute your process
3) Click stop button
4) Then, the result voltage/current output file will be created as /sdcard/energy.csv

## plot
1) cd ./script
2) pull.sh
3) python3 plot.py
(You should previously install pandas, numpy, matplotlib with pip3)

