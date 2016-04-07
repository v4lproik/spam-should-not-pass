--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: Context; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE "Context" (
    id uuid NOT NULL,
    name text,
    "lastUpdate" timestamp with time zone,
    date timestamp with time zone,
    "userId" uuid
);


ALTER TABLE "Context" OWNER TO root;

--
-- Name: Rule; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE "Rule" (
    id uuid NOT NULL,
    name text,
    rule text,
    date timestamp with time zone,
    "lastUpdate" timestamp with time zone,
    "userId" uuid,
    type integer
);


ALTER TABLE "Rule" OWNER TO root;

--
-- Name: RulesInContext; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE "RulesInContext" (
    "idRule" uuid,
    "idContext" uuid
);


ALTER TABLE "RulesInContext" OWNER TO root;

--
-- Name: Scheme; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE "Scheme" (
    id uuid NOT NULL,
    properties text,
    "userId" uuid,
    date timestamp with time zone,
    "lastUpdate" timestamp with time zone,
    type integer
);


ALTER TABLE "Scheme" OWNER TO root;

--
-- Name: User; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE "User" (
    id uuid NOT NULL,
    firstname text,
    lastname text,
    nickname text,
    email text,
    permission text,
    status text,
    password text,
    date timestamp with time zone,
    corporation text,
    api_key text
);


ALTER TABLE "User" OWNER TO root;

--
-- Data for Name: Context; Type: TABLE DATA; Schema: public; Owner: root
--

COPY "Context" (id, name, "lastUpdate", date, "userId") FROM stdin;
\.


--
-- Data for Name: Rule; Type: TABLE DATA; Schema: public; Owner: root
--

COPY "Rule" (id, name, rule, date, "lastUpdate", "userId", type) FROM stdin;
\.


--
-- Data for Name: RulesInContext; Type: TABLE DATA; Schema: public; Owner: root
--

COPY "RulesInContext" ("idRule", "idContext") FROM stdin;
\.


--
-- Data for Name: Scheme; Type: TABLE DATA; Schema: public; Owner: root
--

COPY "Scheme" (id, properties, "userId", date, "lastUpdate", type) FROM stdin;
\.


--
-- Data for Name: User; Type: TABLE DATA; Schema: public; Owner: root
--

COPY "User" (id, firstname, lastname, nickname, email, permission, status, password, date, corporation, api_key) FROM stdin;
\.

--
-- Name: Context_pkey; Type: CONSTRAINT; Schema: public; Owner: root; Tablespace:
--

ALTER TABLE ONLY "Context"
ADD CONSTRAINT "Context_pkey" PRIMARY KEY (id);


--
-- Name: Rule_pkey; Type: CONSTRAINT; Schema: public; Owner: root; Tablespace:
--

ALTER TABLE ONLY "Rule"
ADD CONSTRAINT "Rule_pkey" PRIMARY KEY (id);


--
-- Name: Scheme_pkey; Type: CONSTRAINT; Schema: public; Owner: root; Tablespace:
--

ALTER TABLE ONLY "Scheme"
ADD CONSTRAINT "Scheme_pkey" PRIMARY KEY (id);


--
-- Name: untitled_table_pkey; Type: CONSTRAINT; Schema: public; Owner: root; Tablespace:
--

ALTER TABLE ONLY "User"
ADD CONSTRAINT untitled_table_pkey PRIMARY KEY (id);


--
-- Name: Context_userId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "Context"
ADD CONSTRAINT "Context_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User"(id);


--
-- Name: RulesInContext_idContext_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "RulesInContext"
ADD CONSTRAINT "RulesInContext_idContext_fkey" FOREIGN KEY ("idContext") REFERENCES "Context"(id);


--
-- Name: RulesInContext_idRule_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "RulesInContext"
ADD CONSTRAINT "RulesInContext_idRule_fkey" FOREIGN KEY ("idRule") REFERENCES "Rule"(id);


--
-- Name: idUser; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "Scheme"
ADD CONSTRAINT "idUser" FOREIGN KEY ("userId") REFERENCES "User"(id);


--
-- Name: userId; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY "Rule"
ADD CONSTRAINT "userId" FOREIGN KEY ("userId") REFERENCES "User"(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

