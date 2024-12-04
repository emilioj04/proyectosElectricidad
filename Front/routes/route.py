from flask import Blueprint, flash, json, request, render_template, redirect, url_for
import requests

router = Blueprint('router', __name__)

URL = 'http://localhost:8080/myapp/'

@router.route('/')
def home():
    return render_template('home.html')


@router.route('/proyectos')
def lista_proyectos(msg=''):
    r = requests.get("http://localhost:8080/myapp/proyecto/all")
    #print(r.json())
    data = r.json()
    return render_template('listaproyectos.html', lista=data["data"], message = msg)

@router.route('/proyectos/ordenar/<metodo>/<atributo>/<tipo>')
def ordenar_proyectos(metodo, atributo, tipo):
    url = URL + "proyecto/order/"+metodo+"/"+atributo+"/"+tipo
    r = requests.get(url)
    data = r.json()
    if(r.status_code == 200):
        flash('Proyectos ordenados correctamente', category='info')
        return render_template('listaproyectos.html', lista=data["data"])
    else:
        flash('No se pudo ordenar', category = 'error')
        return redirect(lista_proyectos)
    


@router.route('/proyecto/<int:id>')
def proyecto(id):
    r = requests.get(f"http://localhost:8080/myapp/proyecto/get/{id}")
    data = r.json()
    return render_template('proyecto.html', item=data["data"])



@router.route('/proyecto/add', methods=['GET'])
def add_proyecto():
    r1 = requests.get("http://localhost:8080/myapp/proyecto/listType")
    tipos = r1.json().get("data", []) if r1.status_code == 200 else []
    print(tipos)
    return render_template('add_proyecto.html', tipos = tipos)

@router.route('/proyecto/add', methods=['POST'])    
def save_proyecto():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"nombre": form["nombre"], "tipoEnergia":form["tipoEnergia"], "tiempoConstruccion":form["tiempoConstruccion"],"tiempoVida": form["tiempoVida"], "inversionTotal": form["inversionTotal"], "capacidadGeneracionDiaria": form["capacidadGeneracionDiaria"], "costoGeneracionDiaria": form["costoGeneracionDiaria"]}
    r = requests.post("http://localhost:8080/myapp/proyecto/save", data=json.dumps(dataForm), headers=headers)
    data = r.json()
    if r.status_code == 200:
        flash('Se guardo correctamente el Proyecto', category = 'info')
        return redirect (url_for('router.lista_proyectos'))
    else:
        flash(str(data["data"]), category = 'error')
        return redirect (url_for('router.add_proyecto'))

@router.route('/proyecto/update/<id>')
def edit_proyecto(id):
    r = requests.get("http://localhost:8080/myapp/proyecto/get/"+id)
    data = r.json()
    if(r.status_code == 200):
        return render_template('update_proyecto.html',proyecto = data["data"])
    else:
        flash(data["data"], category='error')
        return redirect(url_for('router.lista_proyectos'))

@router.route('/proyecto/update', methods=['POST'])
def update_proyecto():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataForm = {"id": form["id"],"nombre": form["nombre"], "tipoEnergia":form["tipoEnergia"], "tiempoConstruccion":form["tiempoConstruccion"],"tiempoVida": form["tiempoVida"], "inversionTotal": form["inversionTotal"], "capacidadGeneracionDiaria": form["capacidadGeneracionDiaria"], "costoGeneracionDiaria": form["costoGeneracionDiaria"]}
    r = requests.post("http://localhost:8080/myapp/proyecto/update", data=json.dumps(dataForm), headers=headers)
    data = r.json()

    if r.status_code == 200:
        flash('Se ha actualizado correctamente', category='info')
        return redirect(url_for('router.lista_proyectos'))
    else:
        flash('No se ha podido actualizar', category='error')
        return redirect(url_for('router.lista_proyectos')) 


@router.route('/proyecto/delete', methods=['POST'])
def delete_proyecto():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"id": form["id"]}
    r = requests.post("http://localhost:8080/myapp/proyecto/delete", data=json.dumps(dataForm), headers=headers)

    if r.status_code == 200:
        flash('Se ha eliminado correctamente', category='info')
    else:
        data = r.json()
        flash(data["data"], category='error')
    return redirect(url_for('router.lista_proyectos'))

@router.route('/proyectos/search/<metodo>/<atributo>/<tipo>/<valor>')
def search_proyectos(metodo, atributo, tipo, valor):
    url = URL + "proyecto/search/" + metodo+"/"+atributo+"/"+tipo+"/"+valor
    r = requests.get(url)
    data = r.json()
    if(r.status_code == 200):
        flash('Proyecto Encontrado', category ='info')
        return render_template('listaproyectos.html', lista=data["data"])
    else : 
        flash('No se ha podido encontrar', category='error')
        return redirect('lista_proyectos')

@router.route('/inversionistas/')
def lista_inversionistas(msg=''):
    r = requests.get("http://localhost:8080/myapp/inversionista/all")
    print(r.json())
    data = r.json()
    return render_template('listainversionistas.html', lista=data["data"], message = msg)

@router.route('/inversionista/<int:id>')
def inversionista(id):
    r = requests.get(f"http://localhost:8080/myapp/inversionista/get/{id}")
    print(r.json())
    data = r.json()
    return render_template('inversionista.html', item=data["data"])


@router.route('/inversionistas/ordenar/<metodo>/<atributo>/<tipo>')
def ordenar_inversionistas(metodo, atributo, tipo):
    url = URL + "inversionista/order/"+metodo+"/"+atributo+"/"+tipo

    r = requests.get(url)
    print(r)
    #print(r.json())

    data = r.json()
    
    if (r.status_code == 200):
        flash('Inversionistas ordenados correctamente', category='info')
        return render_template('listainversionistas.html', lista=data["data"])
    else:
        flash('No se pudo ordenar', category = 'error')
        return redirect(lista_inversionistas)
    
@router.route('/inversionistas/search/<metodo>/<atributo>/<tipo>/<valor>')
def search_inversionistas(metodo, atributo, tipo, valor):
    url = URL + "inversionista/search/" + metodo+"/"+atributo+"/"+tipo+"/"+valor

    r = requests.get(url)
    data = r.json()

    if (r.status_code == 200):
        flash('Inversionista Encontrado', category ='info')
        return render_template('listainversionistas.html', lista=data["data"])
    else : 
        flash('No se ha podido encontrar', category='error')
        return redirect('lista_inversionista')


@router.route('/inversionista/add', methods=['GET'])
def add_inversionista():
    r1 = requests.get("http://localhost:8080/myapp/inversionista/listTypeIdentificacion")
    r2 = requests.get("http://localhost:8080/myapp/inversionista/listTypeInversionista")

    tipos_identificacion = r1.json().get("data", []) if r1.status_code == 200 else []
    tipos_inversionista = r2.json().get("data", []) if r2.status_code == 200 else []
    return render_template('add_inversionista.html', tipos_identificacion=tipos_identificacion, tipos_inversionista=tipos_inversionista
    )


@router.route('/inversionista/add', methods=['POST'])
def save_inversionista():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"nombre": form["nombre"], "apellido": form ["apellido"], "tipoIdentificacion":form["tipoIdentificacion"],"identificacion":form["identificacion"],"tipoInversionista": form["tipoInversionista"]}
    r = requests.post("http://localhost:8080/myapp/inversionista/save", data=json.dumps(dataForm), headers=headers)
    data = r.json()
    if r.status_code == 200:
        flash('Se guardo correctamente el inversionista', category = 'info')
        return redirect (url_for('router.lista_inversionistas'))
    else:
        flash(str(data["data"]), category = 'error')
        return redirect (url_for('router.add_inversionista'))
    
    

@router.route('/inversionista/delete', methods=['POST'])
def delete_inversionista():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"id": form["id"]}
    r = requests.post("http://localhost:8080/myapp/inversionista/delete", data=json.dumps(dataForm), headers=headers)

    if r.status_code == 200:
        flash('Se ha eliminado correctamente', category='info')
    else:
        data = r.json()
        flash(data["data"], category='error')
    return redirect(url_for('router.lista_inversionistas'))
    

@router.route('/inversionista/actualizar/<id>')
def edit_inversionista(id):
    r = requests.get("http://localhost:8080/myapp/inversionista/get/"+id)
    r1 = requests.get("http://localhost:8080/myapp/inversionista/listTypeIdentificacion")
    r2 = requests.get("http://localhost:8080/myapp/inversionista/listTypeInversionista")
    data = r.json()
    tipos_identificacion = r1.json().get("data", []) if r1.status_code == 200 else []
    tipos_inversionista = r2.json().get("data", []) if r2.status_code == 200 else []
    if(r.status_code == 200):
        return render_template('update_inversionista.html',inversionista = data["data"], tipos_identificacion=tipos_identificacion, tipos_inversionista=tipos_inversionista)
    else:
        flash(data["data"], category='error')
        return redirect(url_for('router.lista_inversionistas'))



@router.route('/inversionista/update', methods=['POST'])
def update_inversionista():
    headers = {'Content-type': 'application/json'}
    form = request.form

    dataF = {"id": form["id"],"nombre": form["nombre"], "apellido": form ["apellido"], "tipoIdentificacion":form["tipoIdentificacion"],"identificacion":form["identificacion"],"tipoInversionista": form["tipoInversionista"]}
    print(dataF)
    r = requests.post("http://localhost:8080/myapp/inversionista/update", data=json.dumps(dataF), headers=headers)
    print(r)
    dat = r.json()
    if(r.status_code == 200):
        flash("Se ha actualizado correctamente", category='info')
        return redirect(url_for('router.lista_inversionistas'))
    else:
        flash(str(dat["data"]), category='error')
        return redirect(url_for('router.lista_inversionistas'))






@router.route('/inversiones/')
def lista_inversiones(msg = ''):
    url = URL + "inversion/all"
    r = requests.get(url)
    inversionistas_res = requests.get("http://localhost:8080/myapp/inversionista/all")
    proyectos_res = requests.get("http://localhost:8080/myapp/proyecto/all")
    
    
    data = r.json().get('data')
    i = inversionistas_res.json().get('data')
    p = proyectos_res.json().get('data')

    print(data)
    print(i)
    print(p)
   
    return render_template('listainversiones.html', inversiones=data, inversionistas=i, proyectos=p)

    

@router.route('/inversion/<int:id>')
def inversion(id):
    r = requests.get(f"http://localhost:8080/myapp/inversion/get/{id}")
    data = r.json()
    return render_template('inversion.html', item=data["data"])

@router.route('/inversiones/ordenar/<metodo>/<atributo>/<tipo>')
def ordenar_inversiones(metodo, atributo, tipo):
    url = URL + "inversion/order/"+metodo+"/"+atributo+"/"+tipo
    r = requests.get(url)
    inversionistas_res = requests.get("http://localhost:8080/myapp/inversionista/all")
    proyectos_res = requests.get("http://localhost:8080/myapp/proyecto/all")
    
    data = r.json().get('data')
    data_i = inversionistas_res.json().get('data')
    data_p = proyectos_res.json().get('data')

    if(r.status_code == 200):
        flash('Inversiones ordenadas', category='info')
        return render_template('listainversiones.html', inversiones = data, inversionistas = data_i, proyectos = data_p)
    else:        
        flash('No se ha podido ordenar', category='error')
        return redirect('lista_inversiones')

@router.route('/inversiones/search/<metodo>/<atributo>/<tipo>/<valor>')
def search_inversiones(metodo, atributo, tipo, valor):
    url = URL + "inversion/search/" + metodo+"/"+atributo+"/"+tipo+"/"+valor
    r = requests.get(url)
    inversionistas_res = requests.get("http://localhost:8080/myapp/inversionista/all")
    proyectos_res = requests.get("http://localhost:8080/myapp/proyecto/all")
    
    data = r.json().get('data')
    data_i = inversionistas_res.json().get('data')
    data_p = proyectos_res.json().get('data')

    if(r.status_code == 200):
        flash('Inversiones encontradas', category='info')
        return render_template('listainversiones.html', inversiones = data, inversionistas = data_i, proyectos = data_p)
    else:        
        flash('No se ha podido encontrar', category='error')
        return redirect('lista_inversiones')

    
@router.route('/inversion/add', methods=['GET'])
def add_inversion():
    return render_template('add_inversion.html')

@router.route('/inversion/save', methods=['POST'])
def save_inversion():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"investor": int(form["idInversionista"]), "project": int(form["idProyecto"]), "montoInvertido": float(form["montoInvertido"]), "fecha": str(form["fecha"])}
    r = requests.post("http://localhost:8080/myapp/inversion/save", data=json.dumps(dataForm), headers=headers)
    data = r.json()
    print(data)
    print(f"ID Inversionista: {form['idInversionista']}")
    print(f"ID Proyecto: {form['idProyecto']}")

    print("lafecha:")
    print(request.form["fecha"])

    if r.status_code == 200:
        flash('Se guardo correctamente la inversion', category = 'info')
        return redirect (url_for('router.lista_inversiones'))
    else:
        print("error aqui")
        flash(str(data["data"]), category = 'error')
        return redirect (url_for('router.add_inversion'))

@router.route('/inversion/delete/<int:id>', methods=['GET', 'POST'])
def delete_inversion(id):
    r = requests.delete(f"http://localhost:8080/myapp/inversion/delete/{id}")
    print(r.json())
    return redirect(url_for('router.lista_inversiones'))

@router.route('/inversion/actualizar/<int:id>')
def edit_inversion(id):
    r = requests.get("http://localhost:8080/myapp/inversion/get/"+str(id))
    data = r.json()
    if(r.status_code == 200):
        return render_template('update_inversion.html',inversion = data["data"])
    else:
        flash(data["data"], category='error')
        return redirect(url_for('router.lista_inversiones'))

@router.route('/inversion/update', methods=['POST'])
def update_inversion():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataForm = {"id": form["id"], "investor": int(form["idInversionista"]), "project": int(form["idProyecto"]), "montoInvertido": float(form["montoInvertido"]), "fecha": str(form["fecha"])}
    print("Datos enviados al servidor externo:", dataForm)
    print(dataForm)
    r = requests.post("http://localhost:8080/myapp/inversion/update", data=json.dumps(dataForm), headers=headers)
    print(r)
    data = r.json()
    if(r.status_code == 200):
        flash("Se ha actualizado correctamente", category='info')
        return redirect(url_for('router.lista_inversiones'))
    else:
        flash(str(data["data"]), category='error')
        print(data)
        return redirect(url_for('router.edit_inversion', id = form["id"]))

    


@router.route('/historial/')
def historial(msg=''):
    r = requests.get("http://localhost:8080/myapp/registro/all")
    print(r.json())
    data = r.json()
    return render_template('historial.html', lista=data["data"], message = msg)
              