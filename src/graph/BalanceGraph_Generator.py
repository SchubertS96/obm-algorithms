import numpy as np

nodeInfo = ""
edges = ""

b = int(input("Enter vertex capacity b: "))
n = (b+1)**b
m = b*n

for i in range(n):
    nodeInfo += str(b)+" 1\n"

r = int(m/(b+1))
start = 0
end = n
for k in range(b):
    for j in range(r):
        for i in range(j, end) :
            edges += str(i) + " " + str(start+j)+"\n"
    end = r
    start = start+r; 
    r = int(b*r/(b+1))
r = int(b**(b+1))
for j in range(r):
    for i in range(end) :
        edges += str(i) + " " + str(start+j)+"\n"


f = open("bal.gr", "w")
f.write(""+str(n)+" "+str(m)+"\n")
f.write(nodeInfo)
f.write(edges)
