import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


data = pd.read_csv("energy.csv")
data.loc[:, 'time'] -= data.iloc[0]['time']
data.loc[:,'voltage'] /= 1000000

plt.subplot(211)
plt.plot(data['time'], data['current'])
plt.xlabel('time(ms)')
plt.ylabel('current(mA)')
plt.ylim(0,2000)

plt.subplot(212)
plt.plot(data['time'], data['voltage'])
plt.xlabel('time(ms)')
plt.ylabel('voltage(V)')
plt.ylim(0,10)

plt.show()
