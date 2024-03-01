import json

import numpy.random

if __name__ == '__main__':
    with open('items_source.json', 'r', encoding='utf-8') as filter_file, open('items.json', 'w',
                                                                               encoding='utf-8') as write_file:
        load_dict = json.load(filter_file)
        region = ["1297", "1298", "1550"]
        type1 = ["1300", "1551", "1552", "1553", "1554", "1555", "1556"]
        year = ["1323", "1324"]
        right = ["1557", "1558"]
        for item in load_dict['items']:
            category = dict()
            category['region'] = ["1296", numpy.random.choice(region)]
            category['type'] = ["1299", numpy.random.choice(type1, p=[0.2, 0.1, 0.07, 0.13, 0.3, 0.12, 0.08])]
            category['year'] = ["1322", numpy.random.choice(year)]
            category['right'] = [numpy.random.choice(right)]
            category['number'] = numpy.random.randint(0, 10000)
            category['date'] = numpy.random.randint(883584001, 1709091101)
            item['genres'] = category
        json.dump(load_dict, write_file, ensure_ascii=False)
