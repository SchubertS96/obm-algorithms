import numpy as np
import math

nodeInfo = ""
edges = ""

n = int(input("Enter number of offline nodes: "))
m = int(input("Enter number of online nodes: "))
b = int(input("Enter maximum vertex capacity: "))
w = int(input("Enter maximum vertex weight: "))
p = float(input("Enter probability that an edge exists between two nodes (format 0.xx): "))

capacities = (np.random.rand(n))*b+1
weights = (np.random.rand(n))*w+1

for i in range(n):
    nodeInfo += str(int(capacities[i]))+" "+str(int(weights[i]))+"\n"
    for j in range(m):
        if np.random.rand(1) < p :
            edges += str(i)+" "+str(j)+"\n"

f = open("rg.gr", "w")
f.write(""+str(n)+" "+str(m)+"\n")
f.write(nodeInfo)
f.write(edges)
