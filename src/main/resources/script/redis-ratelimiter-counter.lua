
local key = KEYS[1];
local limit = tonumber(string.sub(KEYS[2],1,string.len(KEYS[2])-5));
local expire = tonumber(string.sub(KEYS[3],1,string.len(KEYS[3])-5));

local hasKey = redis.call('EXISTS',key);

if hasKey == 1 then
    local value = tonumber(redis.call('GET',key));
    if value >= limit then
        return -1;
    end
end
redis.call('INCR',key);


local ttl = redis.call('TTL',key);
if ttl < 0 then
    redis.call('EXPIRE',key,expire);
end

return 1;
