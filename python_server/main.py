# coding:utf-8
import json

from flask import Flask, request

app = Flask(__name__)


# http://127.0.0.1:8080/api/frontpage
@app.route('/api/frontpage', methods=['GET'])
def get_frontpage():
    result = dict()
    result['title'] = "三年纪节目"
    with open('tables.json', 'r', encoding='utf-8') as tables_file:
        tables_dict = json.load(tables_file)
        result['select_options'] = [[{'id': item['id'], 'title': item['title']} for item in ls] for ls in
                                    tables_dict['select_options']]
    result['items'] = [{'id': item['id'], 'title': item['title'], 'imageURL': item['images']['poster']['url']} for item in
                       item_filter(region[0], type1[0], year[0], right[0])]
    return json.dumps(result, ensure_ascii=False)


# http://127.0.0.1:8080/api/filter?region=1296&type=1299&year=1322&right=1557&sort=
@app.route('/api/filter', methods=['GET'])
def get_filter():
    region_ = request.args.get("region")
    type_ = request.args.get("type")
    year_ = request.args.get("year")
    right_ = request.args.get("right")
    sort = request.args.get("sort")
    result = dict()
    result['items'] = [{'id': item['id'], 'title': item['title'], 'imageURL': item['images']['poster']['url']} for item in
                       item_filter(region_, type_, year_, right_, sort)]
    return json.dumps(result, ensure_ascii=False)


# http://127.0.0.1:8080/api/content?id=1296&title=1299
@app.route('/api/content', methods=['GET'])
def get_content():
    itemId = request.args.get("id")
    itemTitle = request.args.get("title")
    with open('mediadetail.json', 'r', encoding='utf-8') as filter_file:
        result = json.load(filter_file)
    return json.dumps(result, ensure_ascii=False)


# sort = "0" 时间排序 "1"为播放量排序
def item_filter(region_, type_, year_, right_, sort = "1372"):
    with open('items.json', 'r', encoding='utf-8') as items_file:
        items_dict = json.load(items_file)
        result = [item for item in items_dict['items'] if region_ in item['genres']['region']
                  and type_ in item['genres']['type']
                  and year_ in item['genres']['year']
                  and right_ in item['genres']['right']]
        if sort == "1373":
            result.sort(key=lambda item: item['genres']['date'], reverse=True)
        elif sort == "1372":
            result.sort(key=lambda item: item['genres']['number'], reverse=True)
    return result


if __name__ == '__main__':
    region = ["1296", "1297", "1298", "1550"]
    type1 = ["1299", "1300", "1551", "1552", "1553", "1554", "1555", "1556"]
    year = ["1322", "1323", "1324"]
    right = ["1557", "1558"]
    app.run(debug=True, host='127.0.0.1', port=8080)
