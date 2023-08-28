//Base Jobs for Win64 only
UE4_27BaseJobs = [
    [job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-Win64-Shipping', params: [string(name: 'city', value: 'jogja'), string(name: 'province', value: 'jogja')]],
    [job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-Win64-Debug', params: [string(name: 'city', value: 'bandung'), string(name: 'province', value: 'west java')]]
]

UE5_0BaseJobs = [
    [job: 'testing/UE5.0/DCL-Sample-Project-UE5.0-Win64-Shipping', params: [string(name: 'city', value: 'semarang'), string(name: 'province', value: 'central java')]],
    [job: 'testing/UE5.0/DCL-Sample-Project-UE5.0-Win64-Debug', params: [string(name: 'city', value: 'surabaya'), string(name: 'province', value: 'east java')]]
]

UE5_1BaseJobs = [
//    [job: 'testing/UE5.1/DCL-Sample-Project-UE5.1-Win64-Shipping', params: [string(name: 'city', value: 'serang'), string(name: 'province', value: 'banten')]],
//    [job: 'testing/UE5.1/DCL-Sample-Project-UE5.1-Win64-Debug', params: [string(name: 'city', value: 'jakarta'), string(name: 'province', value: 'dki jakarta')]]
]

UE5_2BaseJobs = [
//    [job: 'testing/UE5.2/DCL-Sample-Project-UE5.2-Win64-Shipping', params: [string(name: 'city', value: 'bali'), string(name: 'province', value: 'bali')]],
//    [job: 'testing/UE5.2/DCL-Sample-Project-UE5.2-Win64-Debug', params: [string(name: 'city', value: 'mataram'), string(name: 'province', value: 'west nusa tenggara')]]
]


//Jobs for other platforms
UE4_27PlatformsJobs = [
    [job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-PS4-Shipping', params: [string(name: 'city', value: 'samarinda'), string(name: 'province', value: 'east borneo')]],
    [job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-PS4-Debug', params: [string(name: 'city', value: 'pontianak'), string(name: 'province', value: 'west borneo')]]
]

UE5_0PlatformsJobs = [
    [job: 'testing/UE5.0/DCL-Sample-Project-UE5.0-PS4-Shipping', params: [string(name: 'city', value: 'makassar'), string(name: 'province', value: 'south sulawesi')]],
    [job: 'testing/UE5.0/DCL-Sample-Project-UE5.0-PS4-Debug', params: [string(name: 'city', value: 'manado'), string(name: 'province', value: 'north sulawesi')]]
]

UE5_1PlatformsJobs = [
 //   [job: 'testing/UE5.1/DCL-Sample-Project-UE5.1-PS4-Shipping', params: [string(name: 'city', value: 'gorontalo'), string(name: 'province', value: 'gorontalo')]],
 //   [job: 'testing/UE5.1/DCL-Sample-Project-UE5.1-PS4-Debug', params: [string(name: 'city', value: 'palu'), string(name: 'province', value: 'west sulawesi')]]
]

UE5_2PlatformsJobs = [
 //   [job: 'testing/UE5.2/DCL-Sample-Project-UE5.2-PS4-Shipping', params: [string(name: 'city', value: 'medan'), string(name: 'province', value: 'north sumatera')]],
 //   [job: 'testing/UE5.2/DCL-Sample-Project-UE5.2-PS4-Debug', params: [string(name: 'city', value: 'padang'), string(name: 'province', value: 'west sumatera')]]
]

