import groovy.transform.Field

@Field
Map jobsToTrigger = [
    [job: 'test-1', params: [string(name: 'city', value: 'bogor'), string(name: 'province', value: 'west java')]],
    [job: 'test-2', params: [string(name: 'city', value: 'jogja',), string(name: 'province', value: 'jogja')]],
    [job: 'test-3', params: [string(name: 'city', value: 'bandung'), string(name: 'province', value: 'west java')]],
    [job: 'test-4', params: [string(name: 'city', value: 'jakarta'), string(name: 'province', value: 'jakarta')]],
    [job: 'test-5', params: [string(name: 'city', value: 'semarang'), string(name: 'province', value: 'central java')]],
    [job: 'test-6', params: [string(name: 'city', value: 'surabaya'), string(name: 'province', value: 'east java')]],
    [job: 'test-7', params: [string(name: 'city', value: 'medan'), string(name: 'province', value: 'east sumatera')]]
]

return this;
