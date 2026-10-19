-- KEYS[1]: Seat Redis Key (e.g., "seat:event_101:A1")
-- ARGV[1]: User ID attempting to book (e.g., "user_123")

local status = redis.call('HGET', KEYS[1], 'status')

if status == 'AVAILABLE' or status == false then
    redis.call('HSET', KEYS[1], 'status', 'LOCKED', 'userId', ARGV[1])
    return 1 -- Success
else
    return 0 -- Failed: Seat is already locked or booked
end