import cv2
import numpy as np
import skimage


def set_endpoint(img, x1, y1, x2, y2):
    cnt = 0
    if abs(x1 - x2) < 2:
        for i in range(y1, y2):
            if not (img[i][x1] == np.array([255, 255, 255])).all():
                cnt += 1
                if cnt >= 20:
                    return x2, i
            else:
                cnt = 0
    if abs(y1 - y2) < 2:
        for i in range(x1, x2):
            if not (img[y1][i] == np.array([255, 255, 255])).all():
                cnt += 1
                if cnt >= 20:
                    return i, y2
            else:
                cnt = 0
    return x2, y2


img = cv2.imread(path)
img_hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV_FULL)
bgd_mask = cv2.inRange(img_hsv, np.array([0, 0, 0]), np.array([170, 120, 170]))
bgd_mask = cv2.morphologyEx(bgd_mask, cv2.MORPH_OPEN, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (3, 3)))

black_pixels_mask = cv2.inRange(img, np.array([0, 0, 0]), np.array([70, 70, 70]))
white_pixels_mask = cv2.inRange(img, np.array([230, 230, 230]), np.array([255, 255, 255]))
final_mask = cv2.max(bgd_mask, black_pixels_mask)
final_mask = cv2.min(final_mask, ~white_pixels_mask)

contours, hierarchy = cv2.findContours(final_mask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
final_contours = [contour for contour in contours if cv2.contourArea(contour) > 500]
img = cv2.drawContours(img, final_contours, -1, (0, 0, 0), thickness=cv2.FILLED)

final_mask = ~final_mask
contours, _ = cv2.findContours(final_mask.copy(), cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
final_contours = [contour for contour in contours if cv2.contourArea(contour) > 500]

img = cv2.drawContours(img, final_contours, -1, (255, 255, 255), thickness=cv2.FILLED)

blur = cv2.GaussianBlur(img, (0, 0), sigmaX=1, sigmaY=1, borderType=cv2.BORDER_DEFAULT)
result = skimage.exposure.rescale_intensity(blur, in_range=(254, 255), out_range=(0, 255))
blur = cv2.blur(result, (10, 10))
result = skimage.exposure.rescale_intensity(blur, in_range=(254, 255), out_range=(0, 255))
cv2.imwrite('res.jpg', result)

img = cv2.imread('res.jpg')
size = img.shape[:2]
cv2.line(img, (0,0), (0, size[0]), (255, 255, 255), 10)
cv2.line(img, (0,0), (size[1], 0), (255, 255, 255), 10)
cv2.line(img, (size[1],size[0]), (size[1], 0), (255, 255, 255), 10)
cv2.line(img, (size[1],size[0]), (0, size[0]), (255, 255, 255), 10)
gray_img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
corners = cv2.goodFeaturesToTrack(gray_img, 150, 0.01, 10)
corners = np.intp(corners)

for i_idx, i in enumerate(corners):
    x1, y1 = i.ravel()
    for j_idx, j in enumerate(corners):
        x2, y2 = j.ravel()
        if i_idx != j_idx:
            if abs(x1 - x2) < 3 or abs(y1 - y2) < 3:
                if x1 <= x2 and y1 <= y2:
                    x2, y2 = set_endpoint(img, x1, y1, x2, y2)
                    cv2.line(img, (x1, y1), (x2, y2), (0, 0, 0), 5)


cimg = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
cimg = ~cimg
_, binary = cv2.threshold(cimg, 100, 255, cv2.THRESH_BINARY)
contours, hierarchy = cv2.findContours(binary, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
final_contours = [contour for contour in contours if 1200 > cv2.contourArea(contour) > 0]
img = cv2.drawContours(img, final_contours, -1, (0, 0, 0), thickness=cv2.FILLED)

ret, bw_img = cv2.threshold(img, 127, 255, cv2.THRESH_BINARY)
binimg = bw_img[:, :, 0]

matrix = []
for i in range(len(binimg)):
    row = []
    for j in range(len(binimg[i])):
        if binimg[i][j] > 128:
            row.append(0)
        else:
            row.append(1)
    matrix.append(row)

cv2.imwrite(path + '_current.jpg', img)