UPDATE public.roles SET name = 'ROLE_SERVICEMAN' where name = 'ROLE_SERVICER';

ALTER TABLE public.reception_campaigns
  ADD priority int DEFAULT 0,
  ADD concurrent_amount int DEFAULT 0;
