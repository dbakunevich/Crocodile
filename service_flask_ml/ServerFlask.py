from http import HTTPStatus

import numpy as np
from flask import Flask, jsonify, request

from Utils import model, сanvas_processing, top_k, names

app = Flask(__name__)


@app.route('/predict', methods=['POST'])
def apicall():
    data = request.get_json()

    list_top_classes = []

    try:
        predictions = model.predict(сanvas_processing(data))

        res = np.array(predictions)[0]
        tmp = np.argpartition(res, -top_k)[-top_k:]
        tmp = (tmp[np.argsort(res[tmp])])

        for i in range(top_k):
            list_top_classes.append(names[tmp[top_k - 1 - i]])

    except Exception as e:
        return bad_data_request(e)

    responses = jsonify(list_top_classes)
    responses.status_code = HTTPStatus.OK

    return responses


@app.errorhandler(Exception)
def bad_data_request(error):
    message_error = [str(x) for x in error.args]
    message = {
        'status': HTTPStatus.BAD_REQUEST,
        'message': 'Bad Request: ' + request.url + '--> Please check your data payload...',
        'error': {
            'type': error.__class__.__name__,
            'message': message_error
        }
    }
    resp = jsonify(message)
    resp.status_code = HTTPStatus.BAD_REQUEST

    return resp


@app.errorhandler(HTTPStatus.BAD_REQUEST)
def bad_request(error=None):
    message = {
        'status': HTTPStatus.BAD_REQUEST,
        'message': 'Bad Request: ' + request.url + '--> Please check your data payload...',
    }
    resp = jsonify(message)
    resp.status_code = HTTPStatus.BAD_REQUEST

    return resp


if __name__ == '__main__':
    app.run(host="127.0.0.1", port=5000)
