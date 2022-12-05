import numpy as np
from keras.metrics import metrics
from keras.saving.save import load_model
from skimage.transform import resize

image_size_by_x = 28
image_size_by_y = 28
top_k = 5
number_of_classes = 345


def top_2_acc(y_true, y_pred):
    return metrics.top_k_categorical_accuracy(y_true, y_pred, k=2)


def top_3_acc(y_true, y_pred):
    return metrics.top_k_categorical_accuracy(y_true, y_pred, k=3)


def top_4_acc(y_true, y_pred):
    return metrics.top_k_categorical_accuracy(y_true, y_pred, k=4)


def top_5_acc(y_true, y_pred):
    return metrics.top_k_categorical_accuracy(y_true, y_pred, k=5)


def find_cords(data, start_cord_one, end_cord_one, start_cord_two, end_cord_two, step):
    for i in range(start_cord_one, end_cord_one, step):
        for j in range(start_cord_two, end_cord_two, step):
            if data[i][j] == 0:
                return i


def сanvas_processing(data):
    data = np.array(data)

    x = find_cords(data, 0, data.shape[0], 0, data.shape[1], 1)

    y = find_cords(data.transpose(), 0, data.shape[1], 0, data.shape[0], 1)

    x1 = find_cords(data, data.shape[0] - 1, 0, data.shape[1] - 1, 0, -1)

    y1 = find_cords(data.transpose(), data.shape[1] - 1, 0, data.shape[0] - 1, 0, -1)

    data = 1 - data[x:x1, y:y1] / 255
    data = resize(data, (image_size_by_x, image_size_by_y), anti_aliasing=True)

    data = np.array(data, dtype=np.float32)

    img = np.reshape(data, (-1, image_size_by_x, image_size_by_y, 1))
    return img


model = load_model('models/modelCNN2.h5',
                   custom_objects={'top_2_acc': top_2_acc, 'top_3_acc': top_3_acc, 'top_4_acc': top_4_acc,
                                   'top_5_acc': top_5_acc})

file1 = open("class_names_ru.txt", "r", encoding='UTF-8')
names = []

for i in range(number_of_classes):
    names.append(file1.readline())
