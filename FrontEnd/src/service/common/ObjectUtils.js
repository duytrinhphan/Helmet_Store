export const deepEqual = (obj1, obj2) => {
    if (typeof obj1 !== typeof obj2) return false;
    if (typeof obj1 !== "object" || obj1 === null || obj2 === null) return obj1 === obj2;
    if (obj1.constructor !== obj2.constructor) return false;
    if (Object.keys(obj1).length !== Object.keys(obj2).length) return false;
    for (const key in obj1) {
        if (!deepEqual(obj1[key], obj2[key])) return false;
    }
    return true;
}