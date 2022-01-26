-- Table: public.Food

-- DROP TABLE IF EXISTS public."Food";

CREATE TABLE IF NOT EXISTS public."Food"
(
    room_no integer NOT NULL,
    item text COLLATE pg_catalog."default" NOT NULL,
    quantity integer,
    price integer
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Food"
    OWNER to postgres;


-- Table: public.Guests

-- DROP TABLE IF EXISTS public."Guests";

CREATE TABLE IF NOT EXISTS public."Guests"
(
    room_no integer NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    age integer,
    gender text COLLATE pg_catalog."default",
    contact real,
    CONSTRAINT "Guest_pkey" PRIMARY KEY (room_no)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Guests"
    OWNER to postgres;


-- Table: public.RoomDetails

-- DROP TABLE IF EXISTS public."RoomDetails";

CREATE TABLE IF NOT EXISTS public."RoomDetails"
(
    room_no integer NOT NULL,
    room_type text COLLATE pg_catalog."default" NOT NULL,
    booked integer DEFAULT 0,
    CONSTRAINT "RoomDetails_pkey" PRIMARY KEY (room_no)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."RoomDetails"
    OWNER to postgres;





