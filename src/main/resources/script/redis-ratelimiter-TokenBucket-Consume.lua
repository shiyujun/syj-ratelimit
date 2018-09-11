-- ????
local key = KEYS[1];
local limit = tonumber(KEYS[2]);

key=key..'_key';
-- ????key
local hasKey = redis.call('EXISTS',key);

if hasKey == 1 then
    -- ????????
    local value = tonumber(redis.call('GET',key));
    if value <= 0 then
        return -1;
    end
else
    redis.call('SET',key,limit);
    redis.call('SET',key..'_limit',limit);
end
-- key??
redis.call('DECR',key);

return 1;
