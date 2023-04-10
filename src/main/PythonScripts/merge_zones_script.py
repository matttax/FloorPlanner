import cv2
import numpy as np

print(path, point1, point2)
img = cv2.imread(path)


gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
th = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)[1]
contours, hierarchy = cv2.findContours(th, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
contours = contours[2:]

contour1 = -1
contour2 = -1

for i, c in enumerate(contours):
  if cv2.pointPolygonTest(c, point1, False) == 1:
    contour1 = i
    break

for i, c in enumerate(contours):
  if cv2.pointPolygonTest(c, point2, False) == 1:
    contour2 = i
    break

c1 = []
c2 = []
for c in contours[contour1]:
  c1.append(tuple(c[0]))

for c in contours[contour2]:
  c2.append(tuple(c[0]))

tolerance = 40

p1 = np.array(c1)[:, np.newaxis, :]
p2 = np.array(c2)[np.newaxis, :, :]
arr1, arr2 = (np.linalg.norm(p1 - p2, axis=2) < tolerance).nonzero()

contor = []
for i in range(len(arr1)):
  contor.append([arr1[i], arr2[i]])

lines = []
for p in contor:
  if c1[p[0]][0]==c2[p[1]][0] or c1[p[0]][1]==c2[p[1]][1]:
    lines.append([c1[p[0]][0], c1[p[0]][1], c2[p[1]][0], c2[p[1]][1]])