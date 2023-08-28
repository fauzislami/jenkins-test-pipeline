//Base Jobs for Win64 only
UE4_27BaseJobs = [
    [job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-Win64-Shipping', params: [string(name: 'city', value: 'jogja'), string(name: 'province', value: 'jogja')]],
    [job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-Win64-Debug', params: [string(name: 'city', value: 'bandung'), string(name: 'province', value: 'west java')]]
]

UE4_27PlatformsJobs = [
    //[job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-PS4-Shipping', params: [string(name: 'city', value: 'samarinda'), string(name: 'province', value: 'east borneo')]],
    //[job: 'testing/UE4.27/DCL-Sample-Project-UE4.27-PS4-Debug', params: [string(name: 'city', value: 'pontianak'), string(name: 'province', value: 'west borneo')]]
]
