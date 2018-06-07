# xjMzo%2FLBk00kwsQo4TucSZU3HAzRNW3W2NsZN8Tb2OHGKJ%2FiNwVwySpPAO3cquznmfrhSF1HjirSI%2BWgPW5DMg%3D%3D

from requests import Request
import requests

req = requests.get(url="http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByName?ServiceKey="
                  "xjMzo%2FLBk00kwsQo4TucSZU3HAzRNW3W2NsZN8Tb2OHGKJ%2FiNwVwySpPAO3cquznmfrhSF1HjirSI%2BWgPW5DMg%3D%3D&stSrch=%EC%84%9C%EB%B6%80")

print(req.text)

