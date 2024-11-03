from flask import Blueprint, flash, request, render_template, redirect, url_for
import requests

router = Blueprint('router', __name__)

@router.route('/')
def home():
    return render_template('home.html')


@router.route('/proyectos')
def lista_proyectos():
    r = requests.get("http://localhost:8080/myapp/proyecto/all")
    print(type(r.json()))
    print(r.json())
    return render_template('listaproyectos.html', list=r.json()['info'])

@router.route('/proyecto/<int:id>')
def proyecto(id):
    r = requests.get(f"http://localhost:8080/myapp/proyecto/get/{id}")
    print(r.json())
    return render_template('proyecto.html', item=r.json()['info'])

@router.route('/proyecto/add', methods=['GET', 'POST'])
def add_proyecto():
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
        r = requests.post("http://localhost:8080/myapp/proyecto/createProyecto", json=data)
        print(r.json())
        return redirect('/proyectos')
    return render_template('add_proyecto.html')

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
def lista_inversionistas():
    r = requests.get("http://localhost:8080/myapp/inversionista/all")
    print(r.json())
    return render_template('listainversionistas.html', list=r.json()['info'])

@router.route('/inversionista/<int:id>')
def inversionista(id):
    r = requests.get(f"http://localhost:8080/myapp/inversionista/get/{id}")
    print(r.json())
    return render_template('inversionista.html', item=r.json()['info'])

@router.route('/inversionista/add', methods=['GET', 'POST'])
def add_inversionista():
    if request.method == 'POST':
        data = {
            'nombre': request.form['nombre'],
            'tipoInversionista': request.form['tipoInversionista'],
        }
        r = requests.post("http://localhost:8080/myapp/inversionista/createInversionista", json=data)
        try:
            response_json = r.json()
            print(response_json)
        except requests.exceptions.JSONDecodeError:
            print(f"Error al decodificar la respuesta JSON: {r.text}")
            return f"Error al guardar el inversionista: {r.status_code} - {r.text}", 500

        return redirect('/inversionistas')
    return render_template('add_inversionista.html')

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
def lista_inversiones():
    r = requests.get("http://localhost:8080/myapp/inversion/all")
    print(r.json())
    return render_template('listainversiones.html', list=r.json()['info'])

@router.route('/inversion/<int:id>')
def inversion(id):
    r = requests.get(f"http://localhost:8080/myapp/inversion/get/{id}")
    print(r.json())
    return render_template('inversion.html', item=r.json()['info'])

@router.route('/inversion/add', methods=['GET', 'POST'])
def add_inversion():
    if request.method == 'POST':
        # Recoger datos del proyecto
        proyecto_data = {
            'nombre': request.form['nombre_proyecto'],
            'tipoEnergia': request.form['tipoEnergia'],
            'tiempoConstruccion': request.form['tiempoConstruccion'],
            'tiempoVida': request.form['tiempoVida'],
            'inversion': request.form['inversion'],
            'capacidadGeneracionDiaria': request.form['capacidadGeneracionDiaria'],
            'costoGeneracionDiaria': request.form['costoGeneracionDiaria']
        }

        # Crear el proyecto
        r_proyecto = requests.post("http://localhost:8080/myapp/proyecto/createProyecto", json=proyecto_data)
        
        # Verificar y procesar la respuesta del proyecto
        try:
            proyecto_info = r_proyecto.json()
            print("Respuesta del proyecto:", proyecto_info)  # Depuración
            if not isinstance(proyecto_info, dict):
                raise ValueError("Respuesta del proyecto no es un objeto válido.")
        except (requests.exceptions.JSONDecodeError, ValueError) as e:
            print(f"Error al decodificar la respuesta JSON del proyecto: {e}")
            return f"Error al guardar el proyecto: {r_proyecto.status_code} - {r_proyecto.text}", 500

        # Recoger datos del inversionista
        inversionista_data = {
            'nombre': request.form['nombre_inversionista'],
            'tipoInversionista': request.form['tipoInversionista']
        }

        # Crear el inversionista
        r_inversionista = requests.post("http://localhost:8080/myapp/inversionista/createInversionista", json=inversionista_data)

        # Verificar y procesar la respuesta del inversionista
        try:
            inversionista_info = r_inversionista.json()
            print("Respuesta del inversionista:", inversionista_info)  # Depuración
            if not isinstance(inversionista_info, dict):
                raise ValueError("Respuesta del inversionista no es un objeto válido.")
        except (requests.exceptions.JSONDecodeError, ValueError) as e:
            print(f"Error al decodificar la respuesta JSON del inversionista: {e}")
            return f"Error al guardar el inversionista: {r_inversionista.status_code} - {r_inversionista.text}", 500

        # Crear la inversión
        inversion_data = {
            'proyecto': proyecto_data,  # Usar el objeto completo del proyecto
            'inversionista': inversionista_data,  # Usar el objeto completo del inversionista
            'montoInvertido': request.form['montoInvertido']
        }
        
        r_inversion = requests.post("http://localhost:8080/myapp/inversion/createInversion", json=inversion_data)

        # Verificar la respuesta de la creación de la inversión
        try:
            print(r_inversion.json())  # Depuración
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