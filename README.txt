# README.txt
#
# Copyright (c) 2012-2021 Rafael Corchuelo.
#
# In keeping with the traditional purpose of furthering education and research, it is
# the policy of the copyright owner to permit non-commercial use and redistribution of
# this software. It has been tested carefully, but it is not guaranteed for any particular
# purposes.  The copyright owner does not offer any warranties or representations, nor do
# they accept any liabilities with respect to them.

This is the Acme Planner, which is the item 2 from the Deliverable 2 (D02 - Devising a WIS) 
for the Design and Testing subject of the Software Engineering curriculum of the University
of Seville.

Github repository link: https://github.com/veris744/Acme-Planner.git
Github release: https://github.com/veris744/Acme-Planner/releases/tag/1.0.1

Credenciales Clever-cloud:
- email: isadede@alum.us.es
- password: contraseñaCC

Hay que escribir /Acme-Planner en la url para que funcione. Se ha modificado la dirección base para que no haya problemas con manager.

Para aplicar la recomendación de fechas, hay que poner una fecha de inicio en el workplan posterior a la fecha de inicio de la tarea más
temprana o poner una fecha de fin en el workplan anterior a la fecha de fin más tardía. Esta recomendación se ha implementado como un
mensaje de error en la validación cuando hay alguna tarea que no este dentro del rango de fechas del workplan.