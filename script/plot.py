import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv("energy.csv")
data.loc[:, 'time'] -= data.iloc[0]['time']
data.loc[:,'voltage'] /= 1000000
# data = data[data['time'] > 3000]
data.loc[:, 'time'] -= data.iloc[0]['time']

data2 = pd.read_csv("energy_monsoon.csv")
data2.loc[:, 'time'] *= 1000
data2.loc[:, 'current'] *= 1000
data2.loc[:, 'time'] -= data2.iloc[0]['time']

etime = max(data.iloc[-1]['time'], data2.iloc[-1]['time'])
data = data[data['time'] < etime]
data2 = data2[data2['time'] < etime]


plt.plot(data2['time'], data2['current'], label='monsoon', c='g')
plt.plot(data['time'], data['current'], label='etool', c='r')

plt.ylabel('Current(mA)')
plt.xlabel('time(ms)')
plt.legend()

plt.ylim(0,3000)
# plt.xlim(0,etime)
plt.xlim(128000,144000)


# plt.subplot(212)
# plt.plot(data['time'], data['voltage'])
# plt.xlabel('time(ms)')
# plt.ylabel('voltage(V)')
# plt.ylim(0,10)

plt.show()
