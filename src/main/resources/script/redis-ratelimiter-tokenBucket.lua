
local key = KEYS[1];
local limit = tonumber(string.sub(KEYS[2],1,string.len(KEYS[2])-5));
local step = tonumber(string.sub(KEYS[3],1,string.len(KEYS[3])-5));
local interval = tonumber(string.sub(KEYS[4],1,string.len(KEYS[4])-5));
local nowTime=tonumber(string.sub(KEYS[5],1,string.len(KEYS[5])-5));

local lastClearTimeKey='syj-rateLimiter-lastClearTime'..key
local lastClearTime=redis.call('GET',lastClearTimeKey);
local hasKey = redis.call('EXISTS',key);

if hasKey == 1 then
    local diff = tonumber(nowTime)-tonumber(lastClearTime);
    local value = tonumber(redis.call('GET',key));
    if  diff >= interval then
            local maxValue = value+math.floor(diff/interval)*step;
            if maxValue > limit then
                value = limit;
            else
                value = maxValue;
            end
            redis.call('SET',lastClearTimeKey,nowTime);
            redis.call('SET',key,value);
    end
    if value <= 0 then
        return -1;
    else
        redis.call('DECR',key);
    end
else
    redis.call('SET',key,limit-1);
    redis.call('SET',lastClearTimeKey,nowTime);
end
return 1;
