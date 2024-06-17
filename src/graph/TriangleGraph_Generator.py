import numpy as np

nodeInfo = ""
edges = ""

n = int(input("Enter number of offline vertices: "))
b = int(input("Enter vertex capacity b: "))
m = b*n

for i in range(n):
    nodeInfo += str(b)+" 1\n"

start = 0
for k in range(n):
    for j in range(b):
        for i in range(k, n) :
            edges += str(i) + " " + str(start+j)+"\n"
    start = start+b; 

f = open("triangle.gr", "w")
f.write(""+str(n)+" "+str(m)+"\n")
f.write(nodeInfo)
f.write(edges)
