-- ????
local key = KEYS[1];
local limit = tonumber(KEYS[2]);
local expire = tonumber(KEYS[3]);
-- ????key
local hasKey = redis.call('EXISTS',key);

if hasKey == 1 then
    -- ????????
    local value = tonumber(redis.call('GET',key));
    if value >= limit then
        return -1;
    end
end
-- key??
redis.call('INCR',key);

-- ??key????
local ttl = redis.call('TTL',key);
if ttl < 0 then
    -- ??key????key??????
    redis.call('EXPIRE',key,expire);
end

return 1;
