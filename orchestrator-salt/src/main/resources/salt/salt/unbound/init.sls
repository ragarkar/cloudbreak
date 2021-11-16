{%- from 'metadata/settings.sls' import metadata with context %}
{%- from 'nodes/settings.sls' import host with context %}

/etc/dhcp/dhclient.d/google_hostname.sh:
  file.managed:
    - makedirs: True
    - source: salt://unbound/dhcp/google_hostname.sh
    - mode: 744

/etc/resolv.conf:
  file.managed:
    - makedirs: True
    - source: salt://unbound/config/resolv.conf
    - template: jinja
    - context:
      private_address: {{ host.private_address }}

/etc/dhcp/dhclient-enter-hooks.bkp:
  file.copy:
    - source: /etc/dhcp/dhclient-enter-hooks

/etc/dhcp/dhclient-enter-hooks:
  file.managed:
    - contents: 'echo "dhclient-enter-hooks is not needed anymore!"'

stop_unbound:
  service.dead:
    - name: unbound
    - enable: False
