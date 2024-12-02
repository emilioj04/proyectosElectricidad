from flask import Blueprint, flash, json, request, render_template, redirect, url_for
import requests

router = Blueprint('router', __name__)

@router.route('/')
def home():
    return render_template('home.html')


@router.route('/proyectos')
def lista_proyectos(msg=''):
    r = requests.get("http://localhost:8080/myapp/proyecto/all")
    print(r.json())
    data = r.json()
    return render_template('listaproyectos.html', lista=data["data"], message = msg)


@router.route('/proyecto/<int:id>')
def proyecto(id):
    r = requests.get(f"http://localhost:8080/myapp/proyecto/get/{id}")
    print(r.json())
    data = r.json()
    return render_template('proyecto.html', item=data["data"])

@router.route('/proyecto/add', methods=['GET'])
def add_proyecto():
    return render_template('add_proyecto.html')

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



@router.route('/proyecto/update/<int:id>', methods=['GET', 'POST'])
def update_proyecto(id):
    if request.method == 'POST':
        data = {
            'nombre': request.form['nombre'],
            'tipoEnergia': request.form['tipoEnergia'],
            'tiempoConstruccion': request.form['tiempoConstruccion'],
            'tiempoVida': request.form['tiempoVida'],
            'inversion': request.form['inversion'],
            'capacidadGeneracionDiaria': request.form['capacidadGeneracionDiaria'],
            'costoGeneracionDiaria': request.form['costoGeneracionDiaria']
        }
        r = requests.put(f"http://localhost:8080/myapp/proyecto/update/{id-1}", json=data)
        print(r.json())
        return redirect(url_for('router.lista_proyectos'))
    else:
        r = requests.get(f"http://localhost:8080/myapp/proyecto/get/{id}")
        proyecto = r.json().get('info', {})
        return render_template('update_proyecto.html', proyecto=proyecto, id=id)


@router.route('/proyecto/delete/<int:id>', methods=['POST'])
def delete_proyecto(id):
    r = requests.delete(f"http://localhost:8080/myapp/proyecto/delete/{id}")
    print(r.json())
    return redirect(url_for('router.lista_proyectos'))

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

@router.route('/inversionista/add', methods=['GET'])
def add_inversionista():
    
    return render_template('add_inversionista.html')


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
    
    

@router.route('/inversionista/delete/<int:id>', methods=['GET', 'POST'])
def delete_inversionista(id):
    r = requests.delete(f"http://localhost:8080/myapp/inversionista/delete/{id}")
    print(r.json())
    return redirect(url_for('router.lista_inversionistas'))

@router.route('/inversionista/update/<int:id>', methods=['GET', 'POST'])
def update_inversionista(id):
    if request.method == 'POST':
        data = {
            'nombre': request.form['nombre'],
            'tipoInversionista': request.form['tipoInversionista'],
        }
        r = requests.put(f"http://localhost:8080/myapp/inversionista/update/{id-1}", json=data)
        print(r.json())
        return redirect(url_for('router.lista_inversionistas'))
    else:
        r = requests.get(f"http://localhost:8080/myapp/inversionista/get/{id}")
        inversionista = r.json().get('info', {})
        return render_template('update_inversionista.html', inversionista=inversionista, id=id)

@router.route('/inversiones/')
def lista_inversiones(msg = ''):
    inversiones_res = requests.get("http://localhost:8080/myapp/inversion/all")
    inversionistas_res = requests.get("http://localhost:8080/myapp/inversionista/all")
    proyectos_res = requests.get("http://localhost:8080/myapp/proyecto/all")

    if (inversiones_res.status_code == 200 and 
        inversionistas_res.status_code == 200 and 
        proyectos_res.status_code == 200):

        inversiones = inversiones_res.json()
        inversionistas = inversionistas_res.json()
        proyectos = proyectos_res.json()

        #buscar el nombre de inversionista y proyecto mediante idInversionista e idProyecto
        for inversion in inversiones['data']:
            for inversionista in inversionistas['data']:
                if inversion['idInversionista'] == inversionista['id']:
                    inversion['nombreInversionista'] = inversionista['nombre']
                    inversion['apellidoInversionista'] = inversionista['apellido']
                    inversion['tipoInversionista'] = inversionista['tipoInversionista']
            for proyecto in proyectos['data']:
                if inversion['idProyecto'] == proyecto['id']:
                    inversion['nombreProyecto'] = proyecto['nombre']
                    inversion['tipoEnergia'] = proyecto['tipoEnergia']
                    inversion['tiempoConstruccion'] = proyecto['tiempoConstruccion']
                    inversion['tiempoVida'] = proyecto['tiempoVida']
                    inversion['inversionTotal'] = proyecto['inversionTotal']
                    inversion['capacidadGeneracionDiaria'] = proyecto['capacidadGeneracionDiaria']
                    inversion['costoGeneracionDiaria'] = proyecto['costoGeneracionDiaria']
            

        return render_template('listainversiones.html', lista = inversiones['data'], message = msg)
    else:
        # Manejar errores de conexión o datos faltantes
        return "Error al obtener datos de inversiones, inversionistas o proyectos.", 500


@router.route('/inversion/<int:id>')
def inversion(id):
    r = requests.get(f"http://localhost:8080/myapp/inversion/get/{id}")
    print(r.json())
    return render_template('inversion.html', item=r.json()['info'])

@router.route('/inversion/add', methods=['GET', 'POST'])
def add_inversion():
    if request.method == 'POST':
        proyecto_data = {
            'nombre': request.form['nombre_proyecto'],
            'tipoEnergia': request.form['tipoEnergia'],
            'tiempoConstruccion': request.form['tiempoConstruccion'],
            'tiempoVida': request.form['tiempoVida'],
            'inversion': request.form['inversion'],
            'capacidadGeneracionDiaria': request.form['capacidadGeneracionDiaria'],
            'costoGeneracionDiaria': request.form['costoGeneracionDiaria']
        }

        r_proyecto = requests.post("http://localhost:8080/myapp/proyecto/createProyecto", json=proyecto_data)
        
        try:
            proyecto_info = r_proyecto.json()
            print("Respuesta del proyecto:", proyecto_info)  
            if not isinstance(proyecto_info, dict):
                raise ValueError("Respuesta del proyecto no es un objeto válido.")
        except (requests.exceptions.JSONDecodeError, ValueError) as e:
            print(f"Error al decodificar la respuesta JSON del proyecto: {e}")
            return f"Error al guardar el proyecto: {r_proyecto.status_code} - {r_proyecto.text}", 500

        inversionista_data = {
            'nombre': request.form['nombre_inversionista'],
            'tipoInversionista': request.form['tipoInversionista']
        }

        r_inversionista = requests.post("http://localhost:8080/myapp/inversionista/createInversionista", json=inversionista_data)

        try:
            inversionista_info = r_inversionista.json()
            print("Respuesta del inversionista:", inversionista_info) 
            if not isinstance(inversionista_info, dict):
                raise ValueError("Respuesta del inversionista no es un objeto válido.")
        except (requests.exceptions.JSONDecodeError, ValueError) as e:
            print(f"Error al decodificar la respuesta JSON del inversionista: {e}")
            return f"Error al guardar el inversionista: {r_inversionista.status_code} - {r_inversionista.text}", 500

        inversion_data = {
            'proyecto': proyecto_data,  
            'inversionista': inversionista_data,  
            'montoInvertido': request.form['montoInvertido']
        }
        
        r_inversion = requests.post("http://localhost:8080/myapp/inversion/createInversion", json=inversion_data)

        try:
            print(r_inversion.json()) 
        except requests.exceptions.JSONDecodeError:
            print(f"Error al decodificar la respuesta JSON de la inversión: {r_inversion.text}")
            return f"Error al guardar la inversión: {r_inversion.status_code} - {r_inversion.text}", 500

        return redirect('/inversiones')

    return render_template('add_inversion.html')

@router.route('/inversion/delete/<int:id>', methods=['GET', 'POST'])
def delete_inversion(id):
    r = requests.delete(f"http://localhost:8080/myapp/inversion/delete/{id}")
    print(r.json())
    return redirect(url_for('router.lista_inversiones'))

@router.route('/inversion/update/<int:id>', methods=['GET', 'POST'])
def update_inversion(id):
    if request.method == 'POST':
        data = {
            'montoInvertido': request.form['montoInvertido']
        }
        r = requests.put(f"http://localhost:8080/myapp/inversion/update/{id-1}", json=data)
        print(r.json())
        return redirect(url_for('router.lista_inversiones'))
    else:
        r = requests.get(f"http://localhost:8080/myapp/inversion/get/{id}")
        inversion = r.json().get('info', {})
        return render_template('update_inversion.html', inversion=inversion, id=id)

@router.route('/historial/')
def historial(msg=''):
    r = requests.get("http://localhost:8080/myapp/registro/all")
    print(r.json())
    data = r.json()
    return render_template('historial.html', lista=data["data"], message = msg)
              