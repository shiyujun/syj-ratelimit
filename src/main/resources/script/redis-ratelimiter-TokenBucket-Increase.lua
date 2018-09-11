-- 获取入参
local step = KEYS[1];
-- 获取所有以_key结尾的key
local keyList=redis.call("keys","*_key")
local nowValue;
local maxValue;
-- 获取所有key
for index,key in pairs(keyList) do
    nowValue=tonumber(redis.call('GET',key))+step;
    maxValue=tonumber(redis.call('GET',key..'_limit'));
    if maxValue > nowValue then
        redis.call('SET',key,nowValue)
    else
        redis.call('SET',key,maxValue)
    end
end
return 1;
