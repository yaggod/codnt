import numpy as np
from scipy.integrate import solve_ivp
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import random

amount = 20#int(input("Введите количество тел:\n"))

def getResult(t, sol, framecount):
    tpoints = np.linspace(0, t[-1], framecount)
    sols = []
    for i in tpoints:
        for j in range(0,len(t)):
            if (i>t[j]):
                print(sol[j])
                Percent = (i-t[j])/(t[j+1]-t[j])
                sols.append((sol[j+1]-sol[j])*Percent + sol[j])
                break
            if (i==t[j]): sols.append(sol[j])
    return sols                
                
                
def Animate(frame):
    for i in range(amount):
        # Balls[i].set_data([sol[i][frame]], [sol[i+amount][frame]])
        Balls[i].set_data([Solution['y'][i][frame]], [Solution['y'][i+amount][frame]])
    

        
        


def solveDiff(t,cort):
    x, y, vx, vy = np.split(cort,4)

    dvy_dt = np.zeros(shape=amount)
    dvx_dt = np.zeros(shape=amount)
    for i in range(amount):
        for j in range(i,amount):
            if (j==i): continue
           
            dx = x[j] - x[i]
            dy = y[j] - y[i]
            zn = (dx*dx + dy*dy)
            if (zn <= (Radiuses[i]+Radiuses[j])):
                zn = Radiuses[i]+Radiuses[j]
                # print(f"zn {zn} меньше, чем {Radiuses[i]*2}")
                
                # F = (1-EnergyCoef)*(Masses[i]-Masses[j])
                # massSumm = Masses[i] + Masses[j]
                # vx[i] *= F/(massSumm) + 2*Masses[j]*vx[j]/(massSumm)
                # vy[i] *= F/(massSumm) + 2*Masses[j]*vy[j]/(massSumm)
                # vx[j] *= -F/(massSumm) + 2*Masses[i]*vx[i]/(massSumm)
                # vy[j] *= -F/(massSumm) + 2*Masses[i]*vy[i]/(massSumm)
                
                # vx[i] = vx[i] * (Masses[i] - Masses[j]) / (Masses[i] + Masses[j]) \
                    # + 2*Masses[j] * vx[j] / (Masses[i] + Masses[j])
            
            zn = zn**1.5
            ch = 6.67e-11*Masses[i]*Masses[j]
            Ax = ch*dx/zn
            Ay = ch*dy/zn
            dvx_dt[i] += Ax
            dvy_dt[i] += Ay
            dvx_dt[j] -= Ax
            dvy_dt[j] -= Ay    
        dvx_dt[i] /= Masses[i]
        dvy_dt[i] /= Masses[i]
    return np.concatenate((vx,vy,x*0,y*0))
    # result = np.concatenate((vx, vy, dvx_dt, dvy_dt))
    # return result
        
class Particle:
    def __init__(self, coordLims,vLims,mass,radius):
        
        self.vx = random.uniform(-vLims, vLims)
        self.vy = random.uniform(-vLims, vLims)
        
        self.init(radius, mass, coordLims)
        
    def __init__(self, coordLims,mass,radius):
        self.vy = 0;self.vx = 0;
        self.init(radius, mass, coordLims)
        
    def setSpeed(self, f):
        angle = random.uniform(-np.pi, np.pi)
        
        self.vx = f*np.sin(angle)
        self.vx = f*np.cos(angle)
        
    def setCircSpeed(self,coef=0.01):
        self.vx = -self.y * coef
        self.vy = self.x * coef
    def coordsSet(self, c):
        self.x = c;
        self.y = c;
    def init(self, radius,mass,coordLims):
        self.x = (random.random()-0.5)*2*coordLims
        self.y = (random.random()-0.5)*2*coordLims
        self.radius = radius       
        self.mass = mass
    
    def toArray(self):
        
        return np.array([self.x, self.y, self.vx, self.vy, self.mass,self.radius])

a, = plt.plot([],[],'-',color='r',label='Ball', ms=2)


Particles = np.zeros(shape=amount, dtype=type(Particle))
Balls = np.zeros(shape=amount, dtype=type(a))
Params = np.zeros(shape=(amount,6))
fig, ax = plt.subplots()

xLim = 700#float(input("Введите пределы координат:\n"))
radius = xLim/40000#float(input("Введите радиус частиц(рекомендуется в 40 раз меньше, чем границы координат):\n"))
mass = 20000000#float(input("Введите массу тел:\n"))
while(True):
    ch = 3#int(input("Скорость:\n1)Фиксирована в случайном направлении\n2)Случайна\n3)Направлена перпендикулярно относительно начального положения:\n"))
    if(ch == 1 or ch == 2 or ch == 3): break;
if(ch == 1):
    vLim = 10#float(input("Скорость в случайном направлении:\n"))
    for i in range(amount):
        Particles[i] = Particle(xLim, mass, radius)
        Particles[i].setSpeed(vLim);
elif(ch==2): 
    vLim = 0#float(input("Введите пределы скорости:\n"))
    for i in range(amount):
        Particles[i] = Particle(xLim, vLim, mass, radius)
elif(ch==3):
    coef = float(input("Введите коэффициент: "))
    for i in range(amount):
        Particles[i] = Particle(xLim, mass, radius)
        Particles[i].setCircSpeed(coef);
        
EnergyCoef = 0#float(input("Какую часть скорости будет терять тело при столкновении?\n"))
        

for i in range(amount):
    Params[i] = Particles[i].toArray();
    ball, = plt.plot([],[],'o', color="r",ms=1)
    Balls[i]  = ball#plt.plot([],[],'o',color='r', ms=1)
    

x0, y0, vx0, vy0, Masses, Radiuses = np.split(Params.T,6)
x0 = x0[0]
y0 = y0[0]
vx0 = vx0[0]
vy0 = vy0[0]
Masses = Masses[0]
Radiuses = Radiuses[0]

crt = np.concatenate((x0, y0, vx0, vy0))

siy = 365*24*60*60
sid = 24*60*60
yearcount = 2

alltime = yearcount*200000

Solution = solve_ivp(solveDiff, (0,alltime),crt)
sol = getResult(Solution['t'], Solution['y'].T, 100)

print("Solved!")


plt.xlim(-xLim*2,xLim*2)
plt.ylim(-xLim*2,xLim*2)

ani = FuncAnimation(fig, Animate, frames=len(Solution['y']))
ani.save("adfkjfk.gif", writer="pillow")

