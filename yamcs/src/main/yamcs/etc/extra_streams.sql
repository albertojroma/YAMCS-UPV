create stream cfdp_in as
select
    packet as pdu
from
    tmr_beacon create stream cfdp_out (
        gentime TIMESTAMP,
        entityId long,
        seqNum int,
        pdu binary
    )
insert into
    cfdp_out
select
    gentime,
    'cfdp-service' as origin,
    seqNum,
    '/yamcs/cfdp/upload' as cmdName,
    pdu as binary
from
    cfdp_out