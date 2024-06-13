--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0 (Debian 16.0-1.pgdg120+1)
-- Dumped by pg_dump version 16.0 (Debian 16.0-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: tour_logs; Type: TABLE; Schema: public; Owner: tourplanner
--

CREATE TABLE public.tour_logs (
    id uuid NOT NULL,
    tour_id uuid NOT NULL,
    "timestamp" timestamp without time zone,
    difficulty integer,
    distance integer,
    "time" integer,
    rating integer,
    outdated boolean DEFAULT false
);


ALTER TABLE public.tour_logs OWNER TO tourplanner;

--
-- Name: tours; Type: TABLE; Schema: public; Owner: tourplanner
--

CREATE TABLE public.tours (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying,
    description text,
    type character varying,
    start_lat double precision,
    start_long double precision,
    end_lat double precision,
    end_long double precision
);


ALTER TABLE public.tours OWNER TO tourplanner;

--
-- Name: tour_logs tour_logs_pk; Type: CONSTRAINT; Schema: public; Owner: tourplanner
--

ALTER TABLE ONLY public.tour_logs
    ADD CONSTRAINT tour_logs_pk PRIMARY KEY (id);


--
-- Name: tours tours_pk; Type: CONSTRAINT; Schema: public; Owner: tourplanner
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT tours_pk PRIMARY KEY (id);


--
-- Name: tour_logs tour_logs_tours_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: tourplanner
--

ALTER TABLE ONLY public.tour_logs
    ADD CONSTRAINT tour_logs_tours_id_fk FOREIGN KEY (tour_id) REFERENCES public.tours(id);


--
-- PostgreSQL database dump complete
--

